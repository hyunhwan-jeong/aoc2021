board = []
new File("day23_sample.in").eachLine { 
    board.add(it.toCharArray())
}

N, M = board.size, board[0].size

A = []
B = []
C = []
D = []

for(i in 0..N-1) for(j in 0..M-1) {
    if(board[i][j] == 'A') A.add([i,j])
    else if(board[i][j] == 'B') B.add([i,j])
    else if(board[i][j] == 'C') C.add([i,j])
    else if(board[i][j] == 'D') D.add([i,j])
}

V = new boolean[N][M]

for(i in 0..N-1) for(j in 0..M-1) {
    if(board[i][j] != '.') V[i][j] = true
}

println V