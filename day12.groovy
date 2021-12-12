graph = [:] 
is_lower = [:] 

new File("day12.in").eachLine {
    println it
    (from, to) = it.split("-")
    if(graph[from] == null) {
        graph[from] = []
    }
    if(graph[to] == null) {
        graph[to] = []
    }
    graph[from].add(to)
    graph[to].add(from)

    is_lower[from] = from.toLowerCase() == from
    is_lower[to] = to.toLowerCase() == to
}

n_visited = [:]
graph.each {
    it -> n_visited[it.key] = 0
}

ret = 0
traverse("start", ["start"])

def traverse(where, path) {
    if(where=="end") {
        ret++
        println path
    } else if(is_lower[where] && n_visited[where] == 0) {
        n_visited[where] = 1
        graph[where].each {
            it -> traverse(it, path + [it])
        }
        n_visited[where] = 0
    } else if(!is_lower[where]) {
        graph[where].each {
            it -> traverse(it, path + [it])
        }
    }
}


n_visited = [:]
graph.each {
    it -> n_visited[it.key] = 0
}

println "traverse2"
ret = 0
traverse2("start", ["start"], false)

def traverse2(where, path, is_twiced) {
    if(where=="end") {
        ret++
        println path
    } else if(is_lower[where]) {
        if(where == "start" || where == "end") {
            if(n_visited[where] == 0) {
                n_visited[where] = 1
                graph[where].each {
                    it -> traverse2(it, path + [it], is_twiced)
                }
                n_visited[where] = 0
            }
        }
        else if(!is_twiced && n_visited[where] == 1) {
            n_visited[where] = 2
            graph[where].each {
                it -> traverse2(it, path + [it], true)
            }
            is_twiced= false
            n_visited[where] = 1
        } else if(n_visited[where] == 0) {
            n_visited[where] = 1
            graph[where].each {
                it -> traverse2(it, path + [it], is_twiced)
            }
            n_visited[where] = 0
        } 
    } else {
        graph[where].each {
            it -> traverse2(it, path + [it], is_twiced)
        }
    }
}

println ret