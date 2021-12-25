board = []
new File("day25_sample.in").eachLine {
    board.add(it.toCharArray())
}

N = board.size()
M = board[0].size()

step = 0
while(true) {
    step++
    //if(step >= 10) break
    for(i in 0..N-1) println board[i]
    println ""

    moved = false
    can_move = new boolean[N][M]

    // if(step>=5) break
    for(i in 0..N-1) {
        for(j in 0..M-1) {
            if(board[i][j] == '>') {
                can_move[i][j] = board[i][(j+1)%M] == '.'
                if(can_move[i][j]) moved = true
            } 
        }
    }
    def new_board = board.clone()
    for(i in 0..N-1) {
        for(j in 0..M-1) {
            if(board[i][j] == '>' && can_move[i][j]) {
                new_board[i][j] = '.'
                new_board[i][(j+1)%M] = '>'
            }
        }
    }

    board = new_board.clone()

    if(step==1) {
        println "debug"
        for(b in board) println b
        println ""
    }
    can_move = new boolean[N][M]


    for(i in 0..N-1) {
        for(j in 0..M-1) {
            if(board[i][j] == 'v') {
                can_move[i][j] = board[(i+1)%N][j] == '.'
                if(can_move[i][j]) moved = true
            }
        }
    }

    new_board = board.clone()
    for(i in 0..N-1) {
        for(j in 0..M-1) {
            if(board[i][j] == 'v' && can_move[i][j]) {
                new_board[i][j] = '.'
                new_board[(i+1)%N][j] = 'v'
            }
        }
    }
    board = new_board.clone()


    if(!moved) break

}

println step