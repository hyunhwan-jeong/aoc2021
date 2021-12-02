def horz = 0
def vert = 0

inp = new File("day02.in").newReader()

while(line = inp.readLine()) {
    (dir, dist) = line.split(" ")
    dist = dist as int
    switch(dir) {
        case "forward":
            horz += dist
            break
        case "up":
            vert -= dist
            break
        case "down":
            vert += dist
            break
    }   
}
 
println horz
println vert
println horz*vert

inp.close()

aim = 0 
horz = 0
depth = 0
inp = new File("day02.in").newReader()

while(line = inp.readLine()) {
    (dir, dist) = line.split(" ")
    dist = dist as int
    switch(dir) {
        case "forward":
            horz += dist
            depth += aim * dist
            break
        case "up":
            // depth -= dist
            aim -= dist
            break
        case "down":
            // depth += dist
            aim += dist
            break
    }   
}

println aim
println horz
println depth
println depth * horz
