
def solve(X) {
    def N = X.size()
    def M = X[0].size()
    println N
    println M
    def C = new int[N][M]

    C[0][0] = 0

    for(i in 0..N-1) {
        for(j in 0..M-1) {
            if(i == 0 && j == 0) C[i][j] = 0
            else if(i == 0) C[i][j] = C[i][j-1] + X[i][j]
            else if(j == 0) C[i][j] = C[i-1][j] + X[i][j]
            else C[i][j] = Math.min(C[i-1][j], C[i][j-1]) + X[i][j]
        }
    }
    C[N-1][M-1]
}

import groovy.transform.Canonical
 
@Canonical
class Node implements Comparable<Node> {
    int cost
    int i 
    int j 
    int compareTo(Node other) {
        return cost <=> other?.cost
    }
}

def solve2(X) {
    def N = X.size()
    def M = X[0].size()
    println N
    println M
    def C = new int[N][M]
    for(i in 0..N-1) for(j in 0..M-1) C[i][j] = 987654321

    C[0][0] = 0
    pq = new PriorityQueue<Node>()
    pq.add(new Node(cost: 0, i:0, j: 0))
    
    di = [-1,1,0,0]
    dj = [0,0,-1,1]
    while(!pq.isEmpty()) {
        def node = pq.poll()
        def i = node.i
        def j = node.j
        if(i == N-1 && j == M-1) return C[i][j]
        if(C[i][j] != node.cost) continue

        for(k in 0..3) {
            def ni = i + di[k]
            def nj = j + dj[k]
            if(ni < 0 || ni >= N || nj < 0 || nj >= M) continue
            if(C[ni][nj] > C[i][j] + X[ni][nj]) {
                C[ni][nj] = C[i][j] + X[ni][nj]
                pq.add(new Node(cost: C[ni][nj], i: ni, j: nj))
            }
        }
    }
    return -987654321
}
B = []

new File("day15.in").eachLine {
    it = it.toCharArray().collect(i->i as int - 48)
    B.add(it)
}

println solve(B)
println solve2(B)

N = B.size()
M = B[0].size()
L = new int[N*5][M*5]

checked = new boolean[N*5][M*5]

inc_i = 0
for(offset_i in [0, N, 2*N, 3*N, 4*N]) {
    inc_j = 0 
    for(offset_j in [0, M, 2*M, 3*M, 4*M]) {
        for(i in 0..N-1) {
            for(j in 0..M-1) {
                ni = i + offset_i
                nj = j + offset_j
                risk = B[i][j] + inc_i + inc_j
                if(risk > 9) risk -= 9
                L[ni][nj] = risk
                assert !checked[ni][nj] : "duplicated"
                checked[ni][nj] = true
            }
        }
        inc_j++
    }
    inc_i++
}


for(i in 0..L.size()-1) {
    for(j in 0..L[0].size()-1) {
        assert checked[i][j] : "not checked"
        assert L[i][j] >= 1 && L[i][j] <= 9 : "invalid"
    }
}
println solve(L)
println solve2(L)
