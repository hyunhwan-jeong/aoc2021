is_folding = false
S = [] as Set

new File("day13.in").eachLine {
    if(it == "") {
        is_folding = true
    } else if(!is_folding) {
        (x, y) = it.split(",").collect(i->i as int)
        S.add([x,y])
    } else {
        (where, much) = it.replace("fold along ", "").split("=")
        much = much as int
        newS = [] as Set
        S.each {
            if(where == "y") {
                (x, y) = it
                if(y > much) {
                    y = 2*much-y
                } 
                newS.add([x,y])
                
            } else {
                (x, y) = it
                if(x > much) {
                    x = 2*much-x
                }
            }
            newS.add([x,y])
        }
        S = newS.clone()
    }
}


max_x = S.collect(i->i[0]).max()
max_y = S.collect(i->i[1]).max()
println max_x
println max_y

B = new char[max_y+1][max_x+1]
for(i in 0..max_y) {
    for(j in 0..max_x) {
        B[i][j] = '.'
    }
}

S.each {
    (x, y) = it
    B[y][x] = "#"
}

for(i in 0..max_y) {
    for(j in 0..max_x) {
        print B[i][j]
    }
    println()
}