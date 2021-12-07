vec = new File("day07.in").readLines().first().split(",").collect{ it -> it as int}

mini = 987654321
for(v in vec) {
    cur = 0
    for(t in vec) {
        cur += Math.abs(v-t)
    }
    mini = Math.min(mini, cur)
}

println mini