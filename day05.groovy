from = []
to = []

new File("day05.in").eachLine { 
    (f, t) = it.split(" -> ") 
    from.add(f.split(",").collect(x->x as int).reverse())
    to.add(t.split(",").collect(x->x as int).reverse())
}

nlines = from.size()

Comparator mc0 = { a, b -> a[0] == b[0] ? 0 : (a[0] < b[0] ? -1 : 1) }
Comparator mc1 = { a, b -> a[1] == b[1] ? 0 : (a[1] < b[1] ? -1 : 1) }
n = Math.max(from.max(mc0)[0], to.max(mc0)[0])+1
m = Math.max(from.max(mc1)[1], to.max(mc1)[1])+1

mat = new int[n][m]

for(i in 0..nlines-1) {
    if(from[i][0] == to[i][0]) {
        for(j in from[i][1]..to[i][1]) {
            mat[from[i][0]][j]++
        }
    } else if(from[i][1] == to[i][1]) {
        for(j in from[i][0]..to[i][0]) {
            mat[j][from[i][1]]++
        }
    } /*else {
        disp_y = from[i][0] < to[i][0] ? 1 : -1
        disp_x = from[i][1] < to[i][1] ? 1 : -1

        c_y = from[i][0]
        c_x = from[i][1]
        do {
            mat[c_y][c_x]++
            c_y += disp_y
            c_x += disp_x
        } while(c_y != to[i][0] || c_x != to[i][1])
    }*/
}

for(i in 0..n-1) {
    println mat[i]
}

ret = 0
for(i in 0..n-1) {
    for(j in 0..m-1) {
        if(mat[i][j]>=2) ++ret
    }
}

println ret