P = [0,0]
new File("day21.in").withReader { r ->
    P[0] = r.readLine().split(": ")[1] as int
    P[1] = r.readLine().split(": ")[1] as int    
}

def solve_part1(p) {
    p[0]--
    p[1]--

    die = 1
    roll = 0
    turn = 0

    s = [0,0]
    while(true) {
        p[turn] = (p[turn] + 3 * die + 3) % 10

        dice = 3 * die + 3
        die += 3
        roll += 3
        s[turn] += p[turn]+1

        if(s[turn]>=1000) break
        turn = 1-turn
    }

    println roll
    println s[1-turn]
    s[1-turn] * roll
}

cache = [:]

def solve_part2(p0, p1, s0, s1, t, r, scoreof = 0) {
    //if(r>100) r -= 100

    if(scoreof==0) {
        if(s0 >= 21) return 1G
        if(s1 >= 21) return 0G
    } else {
        if(s1 >= 21) return 1G
        if(s0 >= 21) return 0G
    }

    def state = [p0, p1, s0, s1, t, r]
    // println state
    if(state in cache) return cache[state]

    cache[state] = 0G

    if(t == 0) {
        cache[state] += solve_part2((p0+1)%10, p1, r == 2 ? s0 + (p0+1)%10+1 : s0, s1, r == 2 ? 1 : 0, (r+1) % 3, scoreof)
        cache[state] += solve_part2((p0+2)%10, p1, r == 2 ? s0 + (p0+2)%10+1 : s0, s1, r == 2 ? 1 : 0, (r+1) % 3, scoreof)
        cache[state] += solve_part2((p0+3)%10, p1, r == 2 ? s0 + (p0+3)%10+1 : s0, s1, r == 2 ? 1 : 0, (r+1) % 3, scoreof)
    } else {
        cache[state] += solve_part2(p0, (p1+1)%10, s0, r == 2 ? s1 + (p1+1)%10+1 : s1, r == 2 ? 0 : 1, (r+1) % 3, scoreof)
        cache[state] += solve_part2(p0, (p1+2)%10, s0, r == 2 ? s1 + (p1+2)%10+1 : s1, r == 2 ? 0 : 1, (r+1) % 3, scoreof)
        cache[state] += solve_part2(p0, (p1+3)%10, s0, r == 2 ? s1 + (p1+3)%10+1 : s1, r == 2 ? 0 : 1, (r+1) % 3, scoreof)
    }
    // println "$state ${cache[state]}"
    cache[state]
}

println "Part 1: " + solve_part1(P)
cache = [:]
part2_p0 = solve_part2(P[0]-1, P[1]-1, 0, 0, 0, 0, 0)
cache = [:]
part2_p1 = solve_part2(P[0]-1, P[1]-1, 0, 0, 0, 0, 1)

println "Part 2: " + (part2_p0 > part2_p1 ? part2_p0 : part2_p1)