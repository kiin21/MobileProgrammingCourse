import java.time.LocalDate

// ======= 3.1 =========

abstract class NhanVien {
    protected var id: Int = 0
    protected var dob: LocalDate = LocalDate.now()
    protected var name: String = ""
    protected var address: String = ""

    constructor()

    constructor(id: Int) {
        this.id = id
    }

    constructor(id: Int, dob: LocalDate) {
        this.id = id; this.dob = dob
    }

    constructor(id: Int, dob: LocalDate, name: String) {
        this.id = id; this.dob = dob; this.name = name
    }

    constructor(id: Int, name: String, dob: LocalDate, address: String) {
        this.id = id
        this.name = name
        this.dob = dob
        this.address = address
    }


    abstract fun calculateSalary(): Double

    open fun input(): Unit {
        print("Nhap ma nhan vien (Int): ")
        id = readLine()!!.toInt()
        print("Nhap ten nhan vien (String): ")
        name = readLine()!!
        print("Nhap ngay sinh (yyyy-mm-dd): ")
        dob = LocalDate.parse(readLine()!!)
        print("Nhap dia chi (String): ")
        address = readLine()!!
    }

    open fun display(): Unit {
        println("Ma nhan vien: $id")
        println("Ten nhan vien: $name")
        println("Ngay sinh: $dob")
        println("Dia chi: $address")
        println("Luong: ${calculateSalary()}")
    }
}

// ===== NHÂN VIÊN SẢN XUẤT =====

class NVSanXuat : NhanVien {
    private var soSP: Int = 0
    private val donGia: Double = 20000.0

    constructor() : super()

    constructor(id: Int) : super(id)

    constructor(id: Int, dob: LocalDate) : super(id, dob)

    constructor(id: Int, dob: LocalDate, name: String) : super(id, dob, name)

    constructor(id: Int, dob: LocalDate, name: String, address: String) : super(id, name, dob, address)

    constructor(id: Int, name: String, dob: LocalDate, address: String, soSP: Int) : super(id, name, dob, address) {
        this.soSP = soSP
    }


    override fun calculateSalary(): Double {
        return soSP * donGia
    }

    override fun input(): Unit {
        super.input()
        print("Nhap so san pham: ")
        soSP = readLine()!!.toInt()
    }

    override fun display(): Unit {
        super.display()
        println("So san pham: $soSP")
    }
}

// ===== NHÂN VIÊN CÔNG NHẬT =====

class NVCongNhat : NhanVien {
    private var soNgayLam: Int = 0
    private val donGia: Double = 300000.0

    constructor() : super()

    constructor(id: Int) : super(id)

    constructor(id: Int, dob: LocalDate) : super(id, dob)

    constructor(id: Int, dob: LocalDate, name: String) : super(id, dob, name)

    constructor(id: Int, dob: LocalDate, name: String, address: String) : super(id, name, dob, address)

    constructor(id: Int, name: String, dob: LocalDate, address: String, soNgayLam: Int) : super(id, name, dob, address) {
        this.soNgayLam = soNgayLam
    }

    override fun calculateSalary(): Double {
        return soNgayLam * donGia
    }

    override fun input(): Unit {
        super.input()
        print("Nhap so ngay lam: ")
        soNgayLam = readLine()!!.toInt()
    }

    override fun display(): Unit {
        super.display()
        println("So ngay lam: $soNgayLam")
    }
}



// ======= 3.2 =========

abstract class DocGia {
    protected var maDocGia: String = ""
    protected var hoTen: String = ""
    protected var ngayHetHan: LocalDate = LocalDate.now()
    protected var gioiTinh: String = ""

    // Constructor 1
    constructor()

    // Constructor 2
    constructor(maDocGia: String) {
        this.maDocGia = maDocGia
    }

    // Constructor 3
    constructor(maDocGia: String, hoTen: String) {
        this.maDocGia = maDocGia
        this.hoTen = hoTen
    }

    // Constructor 4
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate) {
        this.maDocGia = maDocGia
        this.hoTen = hoTen
        this.ngayHetHan = ngayHetHan
    }

    // Constructor 5
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate, gioiTinh: String) {
        this.maDocGia = maDocGia
        this.hoTen = hoTen
        this.ngayHetHan = ngayHetHan
        this.gioiTinh = gioiTinh
    }

    abstract fun tinhLePhi(): Double

    open fun nhapThongTin() {
        print("Nhap ma doc gia: ")
        maDocGia = readLine()!!
        print("Nhap ho ten: ")
        hoTen = readLine()!!
        print("Nhap ngay het han (yyyy-mm-dd): ")
        ngayHetHan = LocalDate.parse(readLine()!!)
        print("Nhap gioi tinh: ")
        gioiTinh = readLine()!!
    }

    open fun xuatThongTin() {
        println("Ma doc gia: $maDocGia")
        println("Ho ten: $hoTen")
        println("Ngay het han: $ngayHetHan")
        println("Gioi tinh: $gioiTinh")
        println("Le phi hang thang: ${tinhLePhi()} VND")
    }
}

// ===== ĐỘC GIẢ THƯỜNG =====
class DocGiaThuong : DocGia {
    private var soSachMuon: Int = 0
    private val phiTrenSach: Double = 5000.0

    // Constructor 1
    constructor() : super()

    // Constructor 2
    constructor(maDocGia: String) : super(maDocGia)

    // Constructor 3
    constructor(maDocGia: String, hoTen: String) : super(maDocGia, hoTen)

    // Constructor 4
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate) : super(maDocGia, hoTen, ngayHetHan)

    // Constructor 5
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate, gioiTinh: String, soSachMuon: Int)
            : super(maDocGia, hoTen, ngayHetHan, gioiTinh) {
        this.soSachMuon = soSachMuon
    }

    override fun tinhLePhi(): Double {
        return soSachMuon * phiTrenSach
    }

    override fun nhapThongTin() {
        super.nhapThongTin()
        print("Nhap so sach muon trong thang: ")
        soSachMuon = readLine()!!.toInt()
    }

    override fun xuatThongTin() {
        super.xuatThongTin()
        println("So sach muon trong thang: $soSachMuon")
    }
}

// ===== ĐỘC GIẢ VIP =====
class DocGiaVIP : DocGia {
    private val phiCoDinh: Double = 50000.0

    // Constructor 1: Không tham số
    constructor() : super()

    // Constructor 2: Chỉ mã độc giả
    constructor(maDocGia: String) : super(maDocGia)

    // Constructor 3: Mã độc giả và họ tên
    constructor(maDocGia: String, hoTen: String) : super(maDocGia, hoTen)

    // Constructor 4: Mã độc giả, họ tên và ngày hết hạn
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate) : super(maDocGia, hoTen, ngayHetHan)

    // Constructor 5: Đầy đủ thông tin
    constructor(maDocGia: String, hoTen: String, ngayHetHan: LocalDate, gioiTinh: String)
            : super(maDocGia, hoTen, ngayHetHan, gioiTinh)

    override fun tinhLePhi(): Double {
        return phiCoDinh
    }

    override fun nhapThongTin() {
        super.nhapThongTin()
        println("Doc gia VIP - Phi co dinh: $phiCoDinh VND")
    }

    override fun xuatThongTin() {
        super.xuatThongTin()
        println("Loai doc gia: VIP")
    }
}

fun main() {
    val dsNhanVien = mutableListOf<NhanVien>()
    print("Nhap so luong nhan vien: ")
    val n1 = readLine()!!.toInt()
    for (i in 1..n1) {
        println("Nhap thong tin nhan vien thu $i:")
        print("Chon loai nhan vien (1 - San xuat, 2 - Cong nhat): ")
        val loai = readLine()!!.toInt()
        val nv: NhanVien = when (loai) {
            1 -> NVSanXuat()
            2 -> NVCongNhat()
            else -> {
                println("Loai nhan vien khong hop le. Mac dinh la NV San xuat.")
                NVSanXuat()
            }
        }
        nv.input()
        dsNhanVien.add(nv)
    }

    println("\n=== THONG TIN NHAN VIEN ===")
    for (nv in dsNhanVien) {
        nv.display()
        println("-------------------------")
    }


    val dsDocGia = arrayListOf<DocGia>()

    print("Nhap so luong doc gia: ")
    val n2 = readLine()!!.toInt()

    for (i in 1..n2) {
        println("\nNhap thong tin doc gia thu $i:")
        print("Chon loai doc gia (1 - Doc gia thuong, 2 - Doc gia VIP): ")
        val loai = readLine()!!.toInt()

        val docGia: DocGia = when (loai) {
            1 -> DocGiaThuong()
            2 -> DocGiaVIP()
            else -> {
                println("Loai doc gia khong hop le. Mac dinh la Doc gia thuong.")
                DocGiaThuong()
            }
        }

        docGia.nhapThongTin()
        dsDocGia.add(docGia)
    }

    println("\n=== THONG TIN DOC GIA ===")
    for ((index, docGia) in dsDocGia.withIndex()) {
        println("Doc gia thu ${index + 1}:")
        docGia.xuatThongTin()
        println("-------------------------")
    }

    // Tính tổng lệ phí
    val tongLePhi = dsDocGia.sumOf { it.tinhLePhi() }
    println("Tong le phi cua tat ca doc gia: $tongLePhi VND")



}
