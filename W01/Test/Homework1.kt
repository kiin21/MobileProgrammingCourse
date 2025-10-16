import kotlin.math.sqrt

// 7. Sap xep mang so nguyen 5 phan tu tang dan
fun mySortAsc(arr: IntArray): IntArray {
    quickSort(arr, 0, arr.size - 1, { a, b -> a < b })
    return arr
}

// 8. Sap xep mang so nguyen 5 phan tu giam dan
fun mySortDesc(arr: IntArray): IntArray {
    quickSort(arr, 0, arr.size - 1, { a, b -> a > b })
    return arr
}

// 9. Sap xep mang so thuc 5 phan tu
fun mySort(arr: IntArray, k: Int): IntArray {
    // k = 0 -> desc
    // k = 1 -> asc
    return when (k) {
        0 -> mySortDesc(arr)
        1 -> mySortAsc(arr)
        else -> throw IllegalArgumentException("k must be 0 or 1")
    }
}

// 10. So doi xung
fun isPalindrome(n: Int): Boolean {
    val str = n.toString()
    var l = 0
    var r = str.length - 1

    while (l < r) {
        if (str[l] != str[r]) return false
        l++
        r--
    }
    return true
}


// 11. So nguyen to
fun isPrime(n: Int): Boolean {
    for (i in 0..sqrt(n.toDouble()).toInt()) {
        if (n % i == 0) return false
    }
    return true
}

// 12. Return tuple {max, max even, max odd, max prime}
fun findTuple(arr: IntArray): Tuple4<Int, Int, Int, Int> {
    var max = 0
    var maxEven = 0
    var maxOdd = 0
    var maxPrime = 0

    for (i in arr) {
        if (i > max) max = i
        if (i % 2 == 0 && i > maxEven) maxEven = i
        if (i % 2 != 0 && i > maxOdd) maxOdd = i
        if (isPrime(i) && i > maxPrime) maxPrime = i
    }

    return Tuple4(max, maxEven, maxOdd, maxPrime)
}

data class Tuple4<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

// 1. Tinh tien taxi
//     1km dau 15000
//     tu km thu 2 tro di 13500
//     tu km thu 6 tro di 11000
//     tu km 120 tro di -> giam 10%
fun taxiFare(km: Int): Double {
    return if (km <= 1) 15000.0 * km
    else if (km < 6) {
        15000.0 + (km - 1) * 13500.0
    } else if (km < 120) {
        15000.0 + 5 * 13500.0 + (km - 6) * 11000.0
    } else {
        (15000.0 + 5 * 13500.0 + (km - 6) * 11000.0) * 0.9
    }
}

// 2. Cho trước đơn giá một cuốn sách, số lượng muốn mua, và ý định của người mua là
//có giao hàng hoặc không. Viết hàm tính phí: nếu giao hàng thì tính thêm 20.000 tiền
//shipping, nhưng nếu đơn hàng trên 100.000 thì giảm 10% số tiền.
fun bookPrice(unitPrice: Double, quantity: Int, isShipping: Boolean): Double {
    val subtotal = unitPrice * quantity
    val withShipping = subtotal + (if (isShipping) 20000.0 else 0.0)

    return when {
        withShipping > 100000.0 -> withShipping * 0.9
        else -> withShipping
    }
}


//3. Cho trước tỷ giá 1usd = 22700vnd. Viết chương trình chuyển đổi từ ngoại tệ sang
//tiền Việt và ngược lại
fun vndToUsd(vnd: Double): Double {
    return vnd / 22700.0
}
fun usdToVnd(usd: Double): Double {
    return usd * 22700.0
}

//4. Cho trước số nguyên dương N. Tìm số nguyên dương k nhỏ nhất sao cho:
//1+2+....+k >N
fun findK(n: Int): Int {
    var k = 0
    var curSum = 0
    while (curSum <= n) {
        k++
        curSum += k
    }
    return k
}
// ====================== UTILS =========================
fun quickSort(arr: IntArray, low: Int, high: Int, comparator: (Int, Int) -> Boolean) {
    if (low < high) {
        val pi = partition(arr, low, high, comparator)

        quickSort(arr, low, pi - 1, comparator)
        quickSort(arr, pi + 1, high, comparator)
    }
}

fun partition(arr: IntArray, low: Int, high: Int, comparator: (Int, Int) -> Boolean): Int {
    val pivot = arr[high]
    var i = low - 1
    for (j in low until high) {
        if (comparator(arr[j], pivot)) {
            i++
            swap(arr, i, j)
        }
    }
    swap(arr, i + 1, high)
    return i + 1
}


fun swap(arr: IntArray, i: Int, j: Int) {
    if (i == j) return

    arr[i] = arr[i] xor arr[j]
    arr[j] = arr[i] xor arr[j]
    arr[i] = arr[i] xor arr[j]
}


fun main() {
    val arr = intArrayOf(5, 3, 8, 1, 2)          // Kotlin style array
    val a: (Int) -> Int = { x -> x * x }         // Lambda function
    println(a(5))                                // prints 25
}
