is_rule = false

polymer = null
rule = [:]

new File("day14.in").eachLine {
    if(it == "") {
        is_rule = true
    } else if(is_rule) {
        (key, value) = it.split( " -> " )
        rule[key as String] = value
    } else {
        polymer = it 
    }
}

for(step in 1..10) {
    new_polymer = ""
    for(i in 0..polymer.length()-2) {
        cur = polymer.substring(i, i+2) as String
        if(i == 0) {
            new_polymer += polymer.substring(i, i+1)
        }
        new_polymer += rule[cur]
        new_polymer += polymer.substring(i+1, i+2)
    }
    polymer = new_polymer
    println polymer
}

uniq = polymer.toCharArray() as Set
ret = []
uniq.each { it1 ->
    cnt = 0
    polymer.toCharArray().collect{ it2 ->
        if(it1 == it2) ++cnt
    }
    ret.add(cnt)
}

ret.sort()
println ret[ret.size()-1] - ret[0]