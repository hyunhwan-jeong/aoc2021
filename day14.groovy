is_rule = false

polymer = null
rule = [:]

new File("day14_sample.in").eachLine {
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
    println "steps... $step"
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
    // println polymer
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

is_rule = false
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

rule[" " + polymer.substring(0, 1)] = polymer.substring(0, 1)
pair_cnt = [:]

polymer = " " + polymer

for(i in 0..polymer.length()-2) {
    cur = polymer.substring(i, i+2) as String
    if(pair_cnt[cur] == null) {
        pair_cnt[cur] = 1 as long
    } else {
        pair_cnt[cur] = pair_cnt[cur] + 1 as long
    }
}

for(step in 1..40) {
    next_pair_cnt = [:]
    for(e in pair_cnt) {
        k = e.key
        v = e.value as long
        if(k.substring(0,1) != " ") {
            new_key = k.substring(0, 1) + rule[k]

            if(next_pair_cnt[new_key] == null) {
                next_pair_cnt[new_key] = v 
            } else {
                next_pair_cnt[new_key] += v 
            }
            new_key = rule[k] + k.substring(1, 2)

            if(next_pair_cnt[new_key] == null) {
                next_pair_cnt[new_key] = v
            } else {
                next_pair_cnt[new_key] += v 
            }
        } else {
            next_pair_cnt[k] = 1 as long
        }
    }

    pair_cnt = next_pair_cnt.clone()

    count = [:]
    for(e in pair_cnt) {
        c = e.key.toCharArray()[1] as String
        v = e.value as long
        println "$e.key $c $v"
        if(count[c] == null) {
            count[c] = v 
        } else {
            count[c] += v 
        }
    }
    println count
    println count.collect{ it.value }.max() - count.collect{ it.value }.min()
}


