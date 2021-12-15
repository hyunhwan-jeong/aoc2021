
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

B = []

new File("day15.in").eachLine {
    it = it.toCharArray().collect(i->i as int - 48)
    B.add(it)
}

println solve(B)

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
