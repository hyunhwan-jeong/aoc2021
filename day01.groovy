def inputs = []

new File("day01.in").eachLine { it ->
  inputs.add(it as int)
}

def ret = 0

for(i in 0..inputs.size()-1) {
    if(inputs[i] < inputs[i+1]) ret += 1
}

println ret