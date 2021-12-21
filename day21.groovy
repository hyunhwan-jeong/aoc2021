p = [0,0]
new File("day21.in").withReader { r ->
    p[0] = r.readLine().split(": ")[1] as int
    p[1] = r.readLine().split(": ")[1] as int    
}
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
println s[1-turn] * roll