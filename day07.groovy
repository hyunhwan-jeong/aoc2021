vec = new File("day07.in").readLines().first().split(",").collect{ it -> it as int}

mini = 987654321 as long
for(v in -vec.max()*2..vec.max()*2) {
    cur = 0 as long
    for(t in vec) {
        diff = Math.abs(v-t)
        cur += (diff+1)*diff/2 as int
    }
    mini = Math.min(mini, cur)
}

println mini