import kotlin.math.abs

/**
 * Lớp đại diện cho một phân số với tử số và mẫu số
 * @property tuSo Tử số của phân số
 * @property mauSo Mẫu số của phân số (phải khác 0)
 */
class PhanSo {
    var tuSo: Int = 1
    var mauSo: Int = 1

    /**
     * Nhập phân số từ bàn phím
     * Yêu cầu nhập lại nếu mẫu số bằng 0
     */
    fun nhap() {
        var valid = false
        while (!valid) {
            print("Nhập tử số: ")
            tuSo = readln().toInt()

            print("Nhập mẫu số: ")
            mauSo = readln().toInt()

            if (mauSo == 0) {
                println("Mẫu số không thể bằng 0. Vui lòng nhập lại!")
            } else {
                valid = true
            }
        }
    }

    /**
     * In phân số ra màn hình dưới dạng tử/mẫu
     */
    fun inPhanSo() {
        if (mauSo == 1) {
            print("$tuSo")
        } else {
            print("$tuSo/$mauSo")
        }
    }

    /**
     * Tìm ước chung lớn nhất của hai số
     * @param a Số thứ nhất
     * @param b Số thứ hai
     * @return Ước chung lớn nhất
     */
    private fun ucln(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        while (y != 0) {
            val temp = y
            y = x % y
            x = temp
        }
        return x
    }

    /**
     * Tối giản phân số
     */
    fun toiGian() {
        val uocChung = ucln(tuSo, mauSo)
        tuSo /= uocChung
        mauSo /= uocChung

        // Đảm bảo mẫu số luôn dương
        if (mauSo < 0) {
            tuSo = -tuSo
            mauSo = -mauSo
        }
    }

    /**
     * So sánh phân số này với phân số khác
     * @param other Phân số cần so sánh
     * @return -1 nếu nhỏ hơn, 0 nếu bằng, 1 nếu lớn hơn
     */
    fun soSanh(other: PhanSo): Int {
        val cross1 = tuSo * other.mauSo
        val cross2 = other.tuSo * mauSo

        return when {
            cross1 < cross2 -> -1
            cross1 > cross2 -> 1
            else -> 0
        }
    }

    /**
     * Tính tổng phân số này với phân số khác
     * @param other Phân số cần cộng
     * @return Phân số kết quả
     */
    fun cong(other: PhanSo): PhanSo {
        val result = PhanSo()
        result.tuSo = tuSo * other.mauSo + other.tuSo * mauSo
        result.mauSo = mauSo * other.mauSo
        result.toiGian()
        return result
    }

    /**
     * Tạo bản sao của phân số
     * @return Bản sao của phân số hiện tại
     */
    fun copy(): PhanSo {
        val copy = PhanSo()
        copy.tuSo = tuSo
        copy.mauSo = mauSo
        return copy
    }
}

/**
 * Chương trình chính thực hiện các yêu cầu với mảng phân số
 */
fun main() {
    println("===== CHƯƠNG TRÌNH QUẢN LÝ PHÂN SỐ =====")

    // 1. Nhập mảng phân số
    print("Nhập số lượng phân số: ")
    val n = readln().toInt()

    val mangPhanSo = Array(n) { PhanSo() }

    println("\n--- NHẬP THÔNG TIN PHÂN SỐ ---")
    for (i in mangPhanSo.indices) {
        println("Phân số thứ ${i + 1}:")
        mangPhanSo[i].nhap()
        println()
    }

    // 2. In mảng phân số vừa nhập
    println("--- MẢNG PHÂN SỐ VỪA NHẬP ---")
    inMangPhanSo(mangPhanSo)

    // 3. Tối giản các phần tử và in kết quả
    println("\n--- MẢNG PHÂN SỐ SAU KHI TỐI GIẢN ---")
    val mangToiGian = mangPhanSo.map { it.copy() }.toTypedArray()
    mangToiGian.forEach { it.toiGian() }
    inMangPhanSo(mangToiGian)

    // 4. Tính tổng các phân số
    println("\n--- TỔNG CÁC PHÂN SỐ ---")
    val tong = tinhTongMangPhanSo(mangPhanSo)
    print("Tổng các phân số: ")
    tong.inPhanSo()
    println()

    // 5. Tìm phân số có giá trị lớn nhất
    println("\n--- PHÂN SỐ LỚN NHẤT ---")
    val maxPhanSo = timPhanSoLonNhat(mangPhanSo)
    print("Phân số lớn nhất: ")
    maxPhanSo.inPhanSo()
    println()

    // 6. Sắp xếp mảng theo thứ tự giảm dần
    println("\n--- MẢNG PHÂN SỐ SẮP XẾP GIẢM DẦN ---")
    val mangSapXep = mangPhanSo.map { it.copy() }.toTypedArray()
    sapXepGiamDan(mangSapXep)
    inMangPhanSo(mangSapXep)

    println("\n===== KẾT THÚC CHƯƠNG TRÌNH =====")
}

/**
 * In mảng phân số ra màn hình
 * @param mang Mảng phân số cần in
 */
fun inMangPhanSo(mang: Array<PhanSo>) {
    mang.forEachIndexed { index, phanSo ->
        print("Phân số ${index + 1}: ")
        phanSo.inPhanSo()
        println()
    }
}

/**
 * Tính tổng của mảng phân số
 * @param mang Mảng phân số cần tính tổng
 * @return Phân số là tổng của các phân số trong mảng
 */
fun tinhTongMangPhanSo(mang: Array<PhanSo>): PhanSo {
    var tong = PhanSo().apply {
        tuSo = 0
        mauSo = 1
    }

    mang.forEach { phanSo ->
        tong = tong.cong(phanSo)
    }

    return tong
}

/**
 * Tìm phân số có giá trị lớn nhất trong mảng
 * @param mang Mảng phân số cần tìm
 * @return Phân số có giá trị lớn nhất
 */
fun timPhanSoLonNhat(mang: Array<PhanSo>): PhanSo {
    var max = mang[0]

    for (i in 1 until mang.size) {
        if (mang[i].soSanh(max) == 1) {
            max = mang[i]
        }
    }

    return max
}

/**
 * Sắp xếp mảng phân số theo thứ tự giảm dần
 * @param mang Mảng phân số cần sắp xếp
 */
fun sapXepGiamDan(mang: Array<PhanSo>) {
    for (i in 0 until mang.size - 1) {
        for (j in i + 1 until mang.size) {
            if (mang[i].soSanh(mang[j]) == -1) {
                // Hoán đổi hai phân số
                val temp = mang[i]
                mang[i] = mang[j]
                mang[j] = temp
            }
        }
    }
}