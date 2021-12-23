board = []
new File("day23.in").eachLine { 
    board.add(it.toCharArray())
}

N = board.size()
M = board[0].size()

index = new int[N][M]
index.each{
    Arrays.fill(it,-1)
}

K = 0

start_state = [[],[],[],[]]

for(i in 0..N-1) for(j in 0..M-1) {
    if(board[i][j] != '#') {
        index[i][j] = K++
        if(board[i][j] != '.') {
            println board[i][j] as char
            start_state[(board[i][j] as char)-('A' as char)].add(index[i][j])
        }
    }
}

dist = new int[K][K]

for(i in 0..N-1) for(j in 0..M-1) if(index[i][j] != -1) {
    for(ii in 0..N-1) for(jj in 0..M-1) if(index[ii][jj] != -1) {
        dist[index[i][j]][index[ii][jj]] = Math.abs(i-ii) + Math.abs(j-jj)
        dist[index[ii][jj]][index[i][j]] = Math.abs(i-ii) + Math.abs(j-jj)
    }
}

println dist


print start_state
near = [] 


for(i in 0..K-1) {
    near.add([])
}

di = [-1,1,0,0]
dj = [0,0,-1,1]

for(i in 0..N-1) for(j in 0..M-1) if(index[i][j]!=-1) {
    for(k in 0..3) {
        ii = i + di[k]
        jj = j + dj[k]
        if(ii>=0 && ii<N && jj>=0 && jj<M && index[ii][jj]!=-1) {
            near[index[i][j]].add(index[ii][jj])
        }
    }
}

println near

GOAL = [[11,15], [12,16], [13,17], [14,18]]
println GOAL
cache = [:]

import groovy.transform.Canonical

cost = [1, 10, 100, 1000]

def calc_approx(cur_state) {
    def cur_dist = 0
    for(i in 0..3) {
        cur_dist += Math.min( dist[cur_state[i][0]][GOAL[i][0]] + dist[cur_state[i][1]][GOAL[i][1]],
                              dist[cur_state[i][0]][GOAL[i][1]] + dist[cur_state[i][1]][GOAL[i][0]]) * cost[i]
    }
    cur_dist
}

@Canonical
class Node implements Comparable<Node> {
    int cost
    int approx
    List state
    int compareTo(Node other) {
        return cost + approx <=> other?.cost + approx
    }
}

println "search starts"
println calc_approx(start_state)
pq = new PriorityQueue<Node>()
pq.add(new Node(cost: 0, state: start_state, approx : calc_approx(start_state)))
cache[start_state] = calc_approx(start_state)

timer = 0 
while(!pq.isEmpty()) {
    def node = pq.poll()
    def state = node.state
    if(state == GOAL) {
        println node.cost
        break
    }
    if(cache[state] < node.cost) continue

    if(timer++ % 1000000 == 0) {
        println "at $timer = $state and $node.cost"
    }

    def visited = new boolean[K]
    // println "testing... at $node.cost"

    for(s in state) {
        visited[s[0]] = true
        visited[s[1]] = true
    }

    def n_at_top = 0
    for(i in 0..10) if(visited[i]) ++n_at_top


    for(i in 0..3) {
        if(state[i]==GOAL[i]) continue
        def a = state[i][0]
        def b = state[i][1]
        if(a != GOAL[i][1]) {
            for(next in near[a]) {
                if(visited[next]) continue
                if(a > 10 && next >= 0 && next <= 10 && n_at_top >= 4) continue

                def new_state = [state[0], state[1], state[2], state[3]] 
                // why is this a problem? new_state[i] = next < b ? (next << 32L) + b : (b << 32L) + next
                if (next < b) {
                    new_state[i] = [next, b]
                } else {
                    new_state[i] = [b, next]
                }
                
                def cur_approx = calc_approx(new_state)
                if(cache.containsKey(new_state) && cache[new_state] <= node.cost + cost[i]) continue
                cache[new_state] = node.cost + cost[i]

                //println "($a and $b) => $next and $b, cost: ${cache[new_state]}, new_state = ${new_state[i]}"

                pq.add(new Node(cost: cache[new_state], state: new_state, approx: cur_approx))
            }
        }
        if(b != GOAL[i][1]) {
            for(next in near[b]) {
                if(visited[next]) continue
                if(b > 10 && next >= 0 && next <= 10 && n_at_top >= 4) continue

                def new_state = [state[0], state[1], state[2], state[3]] 

                if (next < a) {
                    new_state[i] = [next, a]
                } else {
                    new_state[i] = [a, next]
                }

                def cur_approx = calc_approx(new_state)
                if(cache.containsKey(new_state) && cache[new_state] <= node.cost + cost[i]) continue
                cache[new_state] = node.cost + cost[i] 

                //println "($a and $b) => $next and $a, cost: ${cache[new_state]}, new_state = ${new_state[i]}"
                pq.add(new Node(cost: cache[new_state], state: new_state, approx: cur_approx))
            }
        }
    }
}
