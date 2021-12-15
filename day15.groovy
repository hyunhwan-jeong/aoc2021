B = []

new File("day15.in").eachLine {
    it = it.toCharArray().collect(i->i as int - 48)
    B.add(it)
}

N = B.size()
M = B[0].size()
C = new int[N][M]

C[0][0] = 0
for(i in 1..N-1) C[i][0] = C[i-1][0] + B[i][0]
for(j in 1..M-1) C[0][j] = C[0][j-1] + B[0][j]

for(i in 1..N-1) {
    for(j in 1..M-1) {
        C[i][j] = Math.min(C[i-1][j], C[i][j-1]) + B[i][j]
    }
}

println C[N-1][M-1]