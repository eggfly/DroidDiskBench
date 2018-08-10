package eggfly.droiddiskbench

import java.io.*
import java.util.*
import kotlin.collections.ArrayList


@Throws(IOException::class)
fun testFileSize(mb: Int): ArrayList<String> {
    val file = File.createTempFile("test", ".txt")
    file.deleteOnExit()
    val chars = CharArray(1024)
    Arrays.fill(chars, 'A')
    val longLine = String(chars)
    val start1 = System.nanoTime()
    val pw = PrintWriter(FileWriter(file))
    for (i in 0 until mb * 1024)
        pw.println(longLine)
    pw.close()
    val time1 = System.nanoTime() - start1
    val results: ArrayList<String> = ArrayList()
    val str1 = String.format("Took %.3f seconds to write to a %d MB, file rate: %.1f MB/s%n",
            time1 / 1e9, file.length() shr 20, file.length() * 1000.0 / time1)
    results.add(str1)
    System.out.println(str1)

    val start2 = System.nanoTime()
    val br = BufferedReader(FileReader(file))
    var line: String? = ""
    while (line != null) {
        line = br.readLine()
    }
    br.close()
    val time2 = System.nanoTime() - start2
    val str2 = String.format("Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
            time2 / 1e9, file.length() shr 20, file.length() * 1000.0 / time2)
    results.add(str2)
    System.out.println(str2)
    file.delete()
    return results
}