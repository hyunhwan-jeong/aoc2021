board = []
new File("day23.in").eachLine { 
    board.add(it.toCharArray())
}

N = board.size()
M = board[0].size()



for(i in 0..N-1) for(j in 0..M-1) {
    if(board[i][j] == ' ') board[i][j] = '#'
}

V = new boolean[N][M]


board = board[1..N-2]
N = board.size()

for(i in 0..N-1) for(j in 0..M-1) {
    if(board[i][j] != '.') V[i][j] = true
}

GOAL = "#...........####A#B#C#D######A#B#C#D###"

cache = [:]
di = [-1,1,0,0]
dj = [0,0,-1,1]

import groovy.transform.Canonical

@Canonical
class Node implements Comparable<Node> {
    int cost
    String state
    int compareTo(Node other) {
        return cost <=> other?.cost
    }
}

def solve(B) {
    def best = null
    def best_hamming = 987654321
    def best_cost = 987654321
    pq = new PriorityQueue<Node>()
    pq.add(new Node(cost: 0, state: B.join("")))

    def timer = 0
    while(!pq.isEmpty()) {
        def node = pq.poll()
        def state = node.state
        if(state == GOAL) return node.cost
        if(state in cache[state] && cache[state][state] < node.cost) continue
        def board = new char[N][M]
        for(i in 0..N-1) for(j in 0..M-1) board[i][j] = state[i*M+j]

        def hamming = 0
        for(i in 0..GOAL.length()-1) {
            if(state[i] != GOAL[i]) hamming++
        }

        if(hamming < best_hamming) {
            best_hamming = hamming
            best = board
            best_cost = node.cost
        }
        if(timer++ % 100000 == 0) {
            for(b in best) println b
            println "diff: $best_hamming, cost: $best_cost"
            println "current $timer -> $node.cost"
        }

        for(i in 0..N-1) for(j in 1..M-2) {
            if(board[i][j] == '.') continue
            if(board[i][j] == '#') continue
            if(i == 2) {
                if(j == 3 && board[i][j] == 'A') continue
                if(j == 5 && board[i][j] == 'B') continue
                if(j == 7 && board[i][j] == 'C') continue
                if(j == 9 && board[i][j] == 'D') continue
            }
            if(i == 1) {
               if(j == 3 && board[i][j] == 'A' && board[i+1][j] == board[i][j]) continue 
               if(j == 5 && board[i][j] == 'B' && board[i+1][j] == board[i][j]) continue 
               if(j == 7 && board[i][j] == 'C' && board[i+1][j] == board[i][j]) continue 
               if(j == 9 && board[i][j] == 'D' && board[i+1][j] == board[i][j]) continue 
            }
            for(k in 0..3) {
                ni = i + di[k]
                nj = j + dj[k]
                if(ni<0||ni>=N) continue
                if(nj<0||nj>=M) continue
                if(board[ni][nj] != '.') continue

                board[ni][nj] = board[i][j]
                board[i][j] = '.'

                cost = 1
                if(board[ni][nj] == 'B') cost = 10
                else if(board[ni][nj]=='C') cost = 100
                else if(board[ni][nj]=='D') cost = 1000

                new_state = board.join("")
                if(!cache.containsKey(new_state) || cache[new_state] > node.cost + cost) {
                    // println "new_state $new_state"
                    cache[new_state] = node.cost + cost
                    pq.add(new Node(cost: node.cost + cost, state: new_state))
                }
                
                board[i][j] = board[ni][nj]
                board[ni][nj] = '.'
            }
        }

    }
    return -1
}

println solve(board)