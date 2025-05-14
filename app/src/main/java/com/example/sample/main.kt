package com.example.sample

fun main() {
    val test1 = intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4)
    val result = test1.copyOfRange(0, removeDuplicates(test1))
    println(result.contentToString())

    val test2 = arrayListOf(7,1,5,3,6,4)
    println(maxProfit(test2))

    val test3 = arrayListOf(1,2,3,4,5,6,7)
    rotate(test3, 3)
    println(test3)

    val num1 = arrayListOf(4, 9, 5)
    val num2 = arrayListOf(9, 4, 9, 8, 4)
    println(intersect(num1, num2))

    val s1 = "racecar"
    val s2 = "carrace"
    println(isAnagram(s1, s2))
}


fun removeDuplicates(nums: IntArray): Int {
    if (nums.isEmpty()) return 0

    var k = 1
    for (i in 1 until nums.size) {
        if (nums[i] != nums[i - 1]) {
            nums[k] = nums[i]
            k++
        }
    }
    return k
}

fun maxProfit(prices: ArrayList<Int>): Int {
    var maxProfit = 0
    for (i in 1 until prices.size) {
        if (prices[i] > prices[i-1]){
            maxProfit += prices[i] - prices[i-1]
        }
    }
    return maxProfit
}

fun rotate(nums: ArrayList<Int>, k: Int) {
    val lastK = ArrayList(nums.takeLast(k))
    // shift right
    for (i in nums.size - 1 downTo k){
        nums[i] = nums[i - k]
    }
    // fill first elemeents
    for(i in lastK.indices){
        nums[i] = lastK[i]
    }
}

fun containsDuplicate(nums: ArrayList<Int>): Boolean {
    val set = HashSet<Int>()
    for (i in nums.indices){
        if (!set.add(nums[i])){
            return true
        }
    }
    return false
}

fun intersect(nums1: ArrayList<Int>, nums2: ArrayList<Int>): ArrayList<Int> {
    val result = ArrayList<Int>()

    val frequencyMap = HashMap<Int, Int>()
    for (num in nums1){
        frequencyMap[num] = frequencyMap.getOrDefault(num, 0) + 1
    }

    for (num in nums2) {
        if (frequencyMap.containsKey(num) && frequencyMap[num]!! > 0){
            result.add(num)
            frequencyMap[num] = frequencyMap[num]!! - 1
        }
    }

    return result
}

fun plusOne(digits: ArrayList<Int>): ArrayList<Int> {
    val n = digits.size

    for (i in n - 1 downTo 0) {
        if (digits[i] < 9) {
            digits[i] += 1
            return digits  // Return early if no carry
        }
        digits[i] = 0  // If digit is 9, set to 0 and continue loop
    }

    // If all digits were 9, we need to add 1 at the beginning (e.g., 999 -> 1000)
    digits.add(0)
    digits[0] = 1
    return digits
}

fun twoSum(nums: ArrayList<Int>, target: Int): IntArray {
    val map = HashMap<Int, Int>()  // Stores number and its index

    for (i in nums.indices) {
        val complement = target - nums[i]  // Find the complement for the current number

        if (map.containsKey(complement)) {
            return intArrayOf(map[complement]!!, i)  // Return indices of the pair
        }

        map[nums[i]] = i  // Store current number and its index
    }

    throw IllegalArgumentException("No two sum solution")  // Should never happen as per problem statement
}

fun reverseString(s: ArrayList<Char>) {
    var left = 0
    var right = s.size - 1

    while (left < right) {
        // Swap characters at left and right indices
        val temp = s[left]
        s[left] = s[right]
        s[right] = temp

        // Move the pointers towards the center
        left++
        right--
    }
}

fun reverse(x: Int): Int {
    var num = x
    var reversed = 0

    while (num != 0) {
        val digit = num % 10  // Extract the last digit
        num /= 10  // Remove the last digit from num

        // Build the reversed number
        reversed = reversed * 10 + digit
    }

    return reversed
}

fun firstUniqueChar(string: String): Int {
    val charCount = HashMap<Char, Int>()
    for (char in string){
        charCount[char] = charCount.getOrDefault(char, 0) + 1
    }
    for (i in string.indices){
        if (charCount[string[i]] == 1){
            return i
        }
    }
    return -1
}

fun isAnagram(s1: String, s2: String): Boolean {
    val frequencyMap = HashMap<Char, Int>()
    for (char in s1){
        frequencyMap[char] = frequencyMap.getOrDefault(char, 0) + 1
    }

    for (char in s2) {
        if (!frequencyMap.containsKey(char) || frequencyMap[char]!! < 0) {
            return false
        }
        frequencyMap[char] = frequencyMap[char]!! - 1
    }
    return true
}

fun myAtoi(s: String): Int {
    val str = s.trim() // Remove leading and trailing spaces
    if (str.isEmpty()) return 0 // Edge case: empty string

    var index = 0
    var sign = 1
    var result = 0

    // Step 1: Check for sign
    if (index < str.length && (str[index] == '+' || str[index] == '-')) {
        sign = if (str[index] == '-') -1 else 1
        index++
    }

    // Step 2: Convert digits to an integer
    while (index < str.length && str[index].isDigit()) {
        val digit = str[index] - '0' // Convert char to int
        // Step 3: Check for overflow before updating result
        if (result > (Int.MAX_VALUE - digit) / 10) {
            return if (sign == 1) Int.MAX_VALUE else Int.MIN_VALUE
        }
        result = result * 10 + digit
        index++
    }

    return result * sign
}

fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.isEmpty()) return "" // Edge case: empty array

    strs.sort() // Sorting helps in minimizing comparisons
    val first = strs.first()
    val last = strs.last()

    val prefix = StringBuilder()

    for (i in first.indices) {
        if (i < last.length && first[i] == last[i]) {
            prefix.append(first[i]) // Build the common prefix
        } else {
            break // Stop on the first mismatch
        }
    }

    return prefix.toString()
}

fun maxSubArray(nums: IntArray): Int {
    if (nums.isEmpty()) return 0 // Edge case: empty array

    var maxSum = nums[0]
    var currentSum = 0

    for(num in nums){
        maxSum = maxOf(maxSum, currentSum + num)
        currentSum = maxOf(0, currentSum + num)
    }
    return maxSum
}