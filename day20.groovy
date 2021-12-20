def solve(filename) {
    def pattern = ""
    def img = []
    new File(filename).withReader { inp ->
        pattern = inp.readLine().toCharArray()
        inp.readLine()
        while(true) {
            line = inp.readLine()
            if(line==null) break
            img.add(line.toCharArray())
        }
    }
    def di = [-1,-1,-1,0,0,0,1,1,1]
    def dj = [-1,0,1,-1,0,1,-1,0,1]

    lights = [] as Set

    for(i in 0..img.size()-1) {
        for(j in 0..img[0].size()-1) {
            if(img[i][j]=='#') {
                lights.add([i,j])
            }
        }
    }

    min_x = -200
    min_y = -200
    max_x = img[0].size() + 200
    max_y = img.size() + 200
    for(step in 1..50) {
        next_lights = [] as Set
        for(i in min_y..max_y) {
            for(j in min_x..max_x) {
                pos = 0
                for(k in 0..8) {
                    ni = i + di[k]
                    nj = j + dj[k]
                    pos *= 2
                    if([ni,nj] in lights) {
                        pos++
                    }
                }
                if(pattern[pos]=='#') {
                    next_lights.add([i,j])
                }
            }
        }
        lights = next_lights.clone()
    }
    for(i in min_y..max_y) {
        for(j in min_x..max_x) {
            if([i,j] in lights) print "#"
            else print "."
        }
        println ""
    }
    lights.size()
}

println solve("day20_sample.in")
println solve("day20.in")