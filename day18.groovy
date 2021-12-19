def add(x, y) {
    if(x == []) return y
    return [x, y]
}

def list_to_tree(l) {
    def tree = new ArrayList(Collections.nCopies(1024+1, null))

    def que = [] as Queue
    max_index = 0
    que.add([l, 1])
    while(que.size() > 0) {
        (x, i) = que.poll()
        if(x.getClass() == Integer) {
            tree[i] = x
            max_index = Math.max(max_index, i)
        } else {
            tree[i] = "pair"
            que.add([x[0], i*2])
            que.add([x[1], i*2+1])
        }   
    }
    tree[0..max_index]
}

def tree_to_list(tree, index = 1) {
    if(tree[index] == "pair") {
        return [tree_to_list(tree, index*2), tree_to_list(tree, index*2+1)]
    } else {
        return tree[index]
    }
}

//assert tree_to_list(list_to_tree([[[[[9,8],1],2],3],4])) == [[[[[9,8],1],2],3],4]
//assert tree_to_list(list_to_tree([[6,[5,[4,[3,2]]]],1])) == [[6,[5,[4,[3,2]]]],1]

def get_order(tree, order, index = 1) {
    if(tree[index] == "pair") {
        get_order(tree, order, index*2)
        get_order(tree, order, index*2+1)
    } else {
        order.add(index)
    }
}
def explode(x) {
    tree = list_to_tree(x)
    order = []
    get_order(tree, order)

    is_exploded = false
    for(i in 0..order.size()-1) {
        if(order[i] % 2 == 0 && order[i] >= 32) {
            if(i == order.size()-1) continue
            if(order[i]+1 != order[i+1]) continue
            is_exploded = true
            parent = order[i].intdiv(2)
            tree[parent] = 0
            left = order[i]
            right = left+1
            left_val = tree[left]
            right_val = tree[right]
            tree[left] = tree[right] = null

            if(i >= 1) {
                tree[order[i-1]] += left_val
            }
            if(i+1 < order.size()-1) {
                tree[order[i+2]] += right_val
            }
            return tree_to_list(tree)
        }
    }
    []
}

// explode([[[[[9,8],1],2],3],4])
// explode([[6,[5,[4,[3,2]]]],1])
// explode([[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]])
assert explode([[[[[9,8],1],2],3],4]) == [[[[0,9],2],3],4]
assert explode([7,[6,[5,[4,[3,2]]]]]) == [7,[6,[5,[7,0]]]]
assert explode([[6,[5,[4,[3,2]]]],1]) == [[6,[5,[7,0]]],3]
assert explode([[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]) == [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]
assert explode([[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]) == [[3,[2,[8,0]]],[9,[5,[7,0]]]]
assert explode([[3,[2,[8,0]]],[9,[5,[7,0]]]]) == []

def split(x) {
    tree = list_to_tree(x)
    order = []
    get_order(tree, order)

    is_exploded = false
    for(i in order) {
        if(tree[i] >= 10) {
            left = tree[i].intdiv(2)
            right = tree[i].intdiv(2)
            if(tree[i]%2==1) right++
            tree[i*2] = left
            tree[i*2+1] = right
            tree[i] = "pair"
            return tree_to_list(tree)
        }
    }
    []
}

assert split([[[[0,7],4],[15,[0,13]]],[1,1]]) == [[[[0,7],4],[[7,8],[0,13]]],[1,1]]
assert split([[[[0,7],4],[[7,8],[0,13]]],[1,1]]) == [[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]

def calc_score(l) {
    if(l.getClass() == Integer) return l
    return calc_score(l[0])*3 + calc_score(l[1])*2
}

def solve(filename) {
    def input = []
    new File(filename).eachLine {
        input.add(Eval.me(it))
    }

    def ret = []
    for(i in input) {
        ret = add(ret, i)

        while(true) {
            ret_explode = explode(ret)
            if(ret_explode != []) {
                ret = ret_explode
                continue
            }
            ret_split = split(ret)
            if(ret_split != []) {
                ret = ret_split
                continue
            }
            break
        }
    }
    println ret
    calc_score(ret)
}

println solve("day18_sample.in")
println solve("day18.in")

