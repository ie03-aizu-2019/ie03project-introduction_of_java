import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";

	static void solve()
	{
		int n = ni(), m = ni(), K = ni();
		int[] from = new int[m];
		int[] to = new int[m];
		int[] w = new int[m];
		for(int i = 0;i < m;i++){
			from[i] = ni();
			to[i] = ni();
			w[i] = ni();
		}
		int[][][] g = packWD(n, from, to, w);
		int[][][] ig = packWD(n, to, from, w);
		kth(g, ig, 0, 0, K);
	}

	public static void kth(int[][][] g, int[][][] ig, int s, int t, int K)
	{
		long I = Long.MAX_VALUE / 3;
		int n = g.length;
		long[] td = new long[n];
		int[] next = new int[n];
		Arrays.fill(next, -1);

		Arrays.fill(td, I);
		MinHeapL h = new MinHeapL(n);
		h.add(t, 0);
		td[t] = 0;

		while(h.size() > 0){
			int cur = h.argmin();
			h.remove(cur);

			for(int[] e : ig[cur]){
				int nex = e[0];
				long nd = td[cur] + e[1];
				if(nd < td[nex]){
					td[nex] = nd;
					next[nex] = cur;
					h.update(nex, nd);
				}
			}
		}

		long[][] delta = new long[n][];
		for(int i = 0;i < n;i++)delta[i] = new long[g[i].length];
		for(int i = 0;i < n;i++){
			for(int j = 0;j < delta[i].length;j++){
				if(td[g[i][j][0]] >= I || td[i] >= I){
					delta[i][j] = I;
				}else{
					delta[i][j] = td[g[i][j][0]] + g[i][j][1] - td[i];
				}
			}
		}

		int[][] prev = makeBucketsRobust(next, n);
		LeftistHeap.Node[] roots = new LeftistHeap.Node[n];

		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(t);
		while(!q.isEmpty()){
			int cur = q.poll();
			boolean alnext = false;
			Queue<LeftistHeap.Node> qnode = new ArrayDeque<LeftistHeap.Node>();
			for(int i = 0;i < delta[cur].length;i++){
				int e = g[cur][i][0];
				if(e == next[cur] && td[cur] == td[e] + g[cur][i][1] && !alnext){
					alnext = true;
					continue;
				}
				if(delta[cur][i] < I)qnode.add(new LeftistHeap.Node(delta[cur][i], cur, e));
			}
			// build heap
			while(qnode.size() > 1){
				qnode.add(LeftistHeap.merge(qnode.poll(), qnode.poll(), false));
			}
			// persistent merge
			if(next[cur] == -1){
				roots[cur] = qnode.poll();
			}else{
				roots[cur] = LeftistHeap.merge(roots[next[cur]], qnode.poll(), true);
			}

			for(int e : prev[cur]){
				q.add(e);
			}
		}

		PriorityQueue<Entry> pq = new PriorityQueue<Entry>(300000, new Comparator<Entry>(){
			public int compare(Entry a, Entry b)
			{
				return Long.compare(a.val, b.val);
			}
		});

		if(roots[s] != null)pq.add(new Entry(roots[s], td[s] + roots[s].key));
		for(int i = 0;i < K;i++){
			if(pq.isEmpty()){
				out.println(-1);
				continue;
			}
			Entry cur = pq.poll(); // K-th
			out.println(cur.val);

			// heap edge
			if(cur.node.left != null){
				pq.add(new Entry(cur.node.left, cur.val + cur.node.left.key - cur.node.key)); // delta of delta
			}
			if(cur.node.right != null){
				pq.add(new Entry(cur.node.right, cur.val + cur.node.right.key - cur.node.key)); // delta of delta
			}

			// outer edge
			if(roots[cur.node.to] != null){
				pq.add(new Entry(roots[cur.node.to], cur.val + roots[cur.node.to].key));
			}
		}
	}

	public static class Entry
	{
		public LeftistHeap.Node node;
		public long val;

		public Entry(LeftistHeap.Node node, long val)
		{
			this.node = node;
			this.val = val;
		}
	}

	public static int[][] makeBucketsRobust(int[] a, int sup)
	{
		int n = a.length;
		int[][] bucket = new int[sup+1][];
		int[] bp = new int[sup+1];
		for(int i = 0;i < n;i++)if(a[i] >= 0)bp[a[i]]++;
		for(int i = 0;i <= sup;i++)bucket[i] = new int[bp[i]];
		for(int i = n-1;i >= 0;i--)if(a[i] >= 0)bucket[a[i]][--bp[a[i]]] = i;
		return bucket;
	}

	public static class LeftistHeap {
		public static class Node
		{
			public long key;
			public Node left, right;
			public int from, to;
			public int dist;

			public Node(long x, int from, int to)
			{
				key = x;
				this.from = from; this.to = to;
				left = right = null;
				dist = 0;
			}

			public Node(Node n)
			{
				key = n.key;
				from = n.from;
				to = n.to;
				left = n.left;
				right = n.right;
				dist = n.dist;
			}

			public void disconnect()
			{
				left = right = null;
				dist = 0;
			}

			public String toString(String indent)
			{
				StringBuilder sb = new StringBuilder();
				if(left != null)sb.append(left.toString(indent + "  "));
				sb.append(indent).append(key).append('\n');
				if(right != null)sb.append(right.toString(indent + "  "));
				return sb.toString();
			}
		}

		public static Node merge(Node m, Node n, boolean persistent)
		{
			if(m == null)return n;
			if(n == null)return m;
			if(m.key > n.key){ // compare
				Node d = m; m = n; n = d;
			}
			if(persistent)m = new Node(m);
			m.right = merge(m.right, n, persistent);
			if(m.left == null || m.right.dist > m.left.dist){
				Node d = m.left; m.left = m.right; m.right = d;
			}
			m.dist = m.right == null ? 0 : m.right.dist + 1;
			return m;
		}
	}

	public static class MinHeapL {
		public long[] a;
		public int[] map;
		public int[] imap;
		public int n;
		public int pos;
		public static long INF = Long.MAX_VALUE;

		public MinHeapL(int m)
		{
			n = Integer.highestOneBit((m+1)<<1);
			a = new long[n];
			map = new int[n];
			imap = new int[n];
			Arrays.fill(a, INF);
			Arrays.fill(map, -1);
			Arrays.fill(imap, -1);
			pos = 1;
		}

		public long add(int ind, long x)
		{
			int ret = imap[ind];
			if(imap[ind] < 0){
				a[pos] = x; map[pos] = ind; imap[ind] = pos;
				pos++;
				up(pos-1);
			}
			return ret != -1 ? a[ret] : x;
		}

		public long update(int ind, long x)
		{
			int ret = imap[ind];
			if(imap[ind] < 0){
				a[pos] = x; map[pos] = ind; imap[ind] = pos;
				pos++;
				up(pos-1);
			}else{
				a[ret] = x;
				up(ret);
				down(ret);
			}
			return x;
		}

		public long remove(int ind)
		{
			if(pos == 1)return INF;
			if(imap[ind] == -1)return INF;

			pos--;
			int rem = imap[ind];
			long ret = a[rem];
			map[rem] = map[pos];
			imap[map[pos]] = rem;
			imap[ind] = -1;
			a[rem] = a[pos];
			a[pos] = INF;
			map[pos] = -1;

			up(rem);
			down(rem);
			return ret;
		}

		public long min() { return a[1]; }
		public int argmin() { return map[1]; }
		public int size() {	return pos-1; }

		private void up(int cur)
		{
			for(int c = cur, p = c>>>1;p >= 1 && a[p] > a[c];c>>>=1, p>>>=1){
				long d = a[p]; a[p] = a[c]; a[c] = d;
				int e = imap[map[p]]; imap[map[p]] = imap[map[c]]; imap[map[c]] = e;
				e = map[p]; map[p] = map[c]; map[c] = e;
			}
		}

		private void down(int cur)
		{
			for(int c = cur;2*c < pos;){
				int b = a[2*c] < a[2*c+1] ? 2*c : 2*c+1;
				if(a[b] < a[c]){
					long d = a[c]; a[c] = a[b]; a[b] = d;
					int e = imap[map[c]]; imap[map[c]] = imap[map[b]]; imap[map[b]] = e;
					e = map[c]; map[c] = map[b]; map[b] = e;
					c = b;
				}else{
					break;
				}
			}
		}
	}
	public static int[][][] packWD(int n, int[] from, int[] to, int[] w) {
		int[][][] g = new int[n][][];
		int[] p = new int[n];
		for(int f : from)
			p[f]++;
		for(int i = 0;i < n;i++)
			g[i] = new int[p[i]][2];
		for(int i = 0;i < from.length;i++){
			--p[from[i]];
			g[from[i]][p[from[i]]][0] = to[i];
			g[from[i]][p[from[i]]][1] = w[i];
		}
		return g;
	}

	public static void main(String[] args) throws Exception
	{
		long S = System.currentTimeMillis();
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);

		solve();
		out.flush();
		long G = System.currentTimeMillis();
		tr(G-S+"ms");
	}

	private static boolean eof()
	{
		if(lenbuf == -1)return true;
		int lptr = ptrbuf;
		while(lptr < lenbuf)if(!isSpaceChar(inbuf[lptr++]))return false;

		try {
			is.mark(1000);
			while(true){
				int b = is.read();
				if(b == -1){
					is.reset();
					return true;
				}else if(!isSpaceChar(b)){
					is.reset();
					return false;
				}
			}
		} catch (IOException e) {
			return true;
		}
	}

	private static byte[] inbuf = new byte[1024];
	static int lenbuf = 0, ptrbuf = 0;

	private static int readByte()
	{
		if(lenbuf == -1)throw new InputMismatchException();
		if(ptrbuf >= lenbuf){
			ptrbuf = 0;
			try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
			if(lenbuf <= 0)return -1;
		}
		return inbuf[ptrbuf++];
	}

	private static boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
//	private static boolean isSpaceChar(int c) { return !(c >= 32 && c <= 126); }
	private static int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }

	private static double nd() { return Double.parseDouble(ns()); }
	private static char nc() { return (char)skip(); }

	private static String ns()
	{
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while(!(isSpaceChar(b))){
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private static char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while(p < n && !(isSpaceChar(b))){
			buf[p++] = (char)b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private static char[][] nm(int n, int m)
	{
		char[][] map = new char[n][];
		for(int i = 0;i < n;i++)map[i] = ns(m);
		return map;
	}

	private static int[] na(int n)
	{
		int[] a = new int[n];
		for(int i = 0;i < n;i++)a[i] = ni();
		return a;
	}

	private static int ni()
	{
		int num = 0, b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}

		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private static long nl()
	{
		long num = 0;
		int b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}

		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private static void tr(Object... o) { if(INPUT.length() != 0)System.out.println(Arrays.deepToString(o)); }
}
