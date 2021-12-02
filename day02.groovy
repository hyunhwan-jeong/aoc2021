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
inp = new File("day02_sample.in").newReader()

while(line = inp.readLine()) {
    (dir, dist) = line.split(" ")
    dist = dist as int
    switch(dir) {
        case "forward":
            horz += dist
            aim *= dist
            break
        case "up":
            aim -= dist
            break
        case "down":
            aim += dist
            break
    }   
}

println aim
println horz
println aim * horz
