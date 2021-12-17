line = new File("day17.in").text

(x1, x2, y1, y2) = line.replace("target area: ", "").replace("x=", "").replace("y=", "").replace("..", " ").replace(", ", " ").split(" ").collect(i->i as int)

def get_max_y(x_velo, y_velo) {
    x = 0
    y = 0
    y_max = 0

    while(1) {
        x += x_velo
        y += y_velo
        y_max = Math.max(y, y_max)
        if(x1 <= x && x <= x2 && y1 <= y && y <= y2) {
            return y_max
        }

        if(y < Math.min(y1, y2)) {
            return -1
        }

        if(x_velo != 0) x_velo = x_velo > 0 ? x_velo - 1 : x_velo + 1
        y_velo--
    }
    return -1
}


best = 0 
for(x in 0..20) {
    for(y in 0..1000) {
        best = Math.max(best,get_max_y(x, y))
        if(best == get_max_y(x, y)) {
            println "best = $best at x: $x, y: $y"
        }
    }
}

println best