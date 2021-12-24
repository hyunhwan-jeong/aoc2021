board = []
new File("day23.in").eachLine { 
    board.add(it.toCharArray())
}

N = board.size()
M = board[0].size()
H = N - 3
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
            // println board[i][j] as char
            start_state[(board[i][j] as char)-('A' as char)].add(index[i][j])
        }
    }
}

for(i in index) {
    println i
}

dist = new int[K][K]

for(i in 0..N-1) for(j in 0..M-1) if(index[i][j] != -1) {
    for(ii in 0..N-1) for(jj in 0..M-1) if(index[ii][jj] != -1) {
        dist[index[i][j]][index[ii][jj]] = Math.abs(i-ii) + Math.abs(j-jj)
        dist[index[ii][jj]][index[i][j]] = Math.abs(i-ii) + Math.abs(j-jj)
    }
}

// println dist


// print start_state
near = [] 


for(i in 0..K-1) {
    near.add([])
}

di = [-1,1,0,0]
dj = [0,0,-1,1]

// println near

GOAL = [[],[],[],[]]
cur_idx = 11
for(j in 0..H-1) {
    for(i in 0..3) {
        GOAL[i].add(cur_idx++)
    }
}

println GOAL

cache = [:]

import groovy.transform.Canonical

cost = [1, 10, 100, 1000]

def calc_approx(cur_state) {
    def cur_dist = 0
    for(i in 0..3) {
        for(j in 0..H-1) {
            cur_dist += dist[cur_state[i][j]][GOAL[i][H-1]] * 2 * cost[i]
        }
    }
    cur_dist
}

@Canonical
class Node implements Comparable<Node> {
    int cost
    int approx
    List state
    int compareTo(Node other) {
        // return cost + approx <=> other?.cost + approx
        return cost <=> other?.cost 
    }
}

println "search starts"
// println calc_approx(start_state)
pq = new PriorityQueue<Node>()
pq.add(new Node(cost: 0, state: start_state, approx : calc_approx(start_state)))
cache[start_state] = calc_approx(start_state)



timer = 0 

def add_node(pq, new_state, node, kind, move_cost) {
    //println "${node.state[kind]} ${new_state[kind]} $move_cost"
    def cur_approx = calc_approx(new_state)
    def new_cost = node.cost + move_cost * cost[kind]
    if(!cache.containsKey(new_state) || cache[new_state] > new_cost) {
        cache[new_state] = new_cost 
        pq.add(new Node(cost: new_cost, state: new_state, approx: cur_approx))
    }
}

def display_status(v) {
    
    print "#"
    for(j in 0..10) if(v[j]==-1) print "." else print v[j] 
    println "#"

    s = 11
    for(k in 0..(H-1)) {
        print "###"
        for(j in s..(s+3)) {
            if(v[j]==-1) print "." else print "${v[j]}"
            if(j != s+3) print "#"
        }
        s += 4
        println "###"
    }

}
def is_feasible(where, target, v) {
    //println "$where, $target, $v" 
    def visited = new int[K]
    for(j in 0..K-1) visited[j] = v[j]
    visited[where] = -1
    visited[target] = -1
    
    while(true) {
        // println "current: $where"
        if(where >= 15) {
            where -= 4
        } else if(where >= 11) {
            where = (where - 10) * 2
        } else {
            if(target < where) where--
            else where++
        }
        //println "$where: ${visited[where]}"
        if(visited[where] != -1) {
            return false
        }
        if(where == target) {
            break
        }
    }
    //print("feasible\n")
    return true
}


while(!pq.isEmpty()) {
    def node = pq.poll()
    def state = node.state

    def visited = new int[K]
    // println "testing... at $node.cost"

    Arrays.fill(visited, -1)
    for(i in 0..3) {
        for(j in 0..(H-1)) {
            visited[state[i][j]] = i
        }
    }
    if(timer == 0) display_status(visited)

    if(state == GOAL) {
        println "answer: $node.cost"
        display_status(visited)
        break
    }
    if(cache[state] < node.cost) continue

    /*if(node.cost < best_approx) {
        best_approx = node.cost
        best_node = node
    }*/
    if(timer++ % 100000 == 0) {
        // println "at $timer = $best_node.state: $best_node.cost [estimated distance: ${best_node.approx}]"
        println "at $timer, searching a node with $node.cost"
        display_status(visited)

    }



    for(i in 0..3) {
        if(state[i]==GOAL[i]) continue
        for(x in 0..H-1) {
            if(state[i][x] <= 10) {
                for(j in (H-1)..0) {
                    if(visited[GOAL[i][j]] == i) continue
                    if(visited[GOAL[i][j]] == -1) {
                        if(is_feasible(GOAL[i][j], state[i][x], visited)) {
                            def new_state = [state[0].clone(), state[1].clone(), state[2].clone(), state[3].clone()]
                            new_state[i][x] = GOAL[i][j]
                            new_state[i].sort()
                            //println "case 1: ${state[i]} ${new_state[i]}"

                            
                            add_node(pq, new_state, node, i, dist[state[i][x]][GOAL[i][j]])
                        }
                    }
                    break
                }
            } else {
                def is_stable = false

                for(j in (H-1)..0) {
                    if(state[i][x] == GOAL[i][j]) {
                        is_stable = true
                        break
                    }
                    if(visited[GOAL[i][j]] != i) {
                        break
                    }                        
                }

                if(is_stable) continue

                for(target in [0,1,3,5,7,9,10]) if(visited[target] == -1) {
                    def where = state[i][x]
                   
                    if(!is_feasible(state[i][x], target, visited)) continue
                    //println "case 2: ${state[i]}"
                    def new_state = [state[0].clone(), state[1].clone(), state[2].clone(), state[3].clone()]
                    new_state[i][x] = target
                    new_state[i].sort()
                    //println "case 2: ${state[i]} ${new_state[i]}"

                    add_node(pq, new_state, node, i, dist[state[i][x]][target])

                }
            }
        }
    }
}
