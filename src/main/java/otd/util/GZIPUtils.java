/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Auther: liangbl
 * @Date: 2018/12/21 13:06
 * @Description:
 */
public class GZIPUtils {

	public static byte compressToBytes(String primStr)[] {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return out.toByteArray();
	}

	/**
	 * 使用gzip进行压缩
	 */
	public static String compress(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Base64.getEncoder().encodeToString(out.toByteArray());
	}

	/**
	 * 使用gzip进行解压缩
	 */
	public static String uncompress(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = Base64.getDecoder().decode(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return decompressed;
	}

//	public static void main(String[] args) {
//		String str = "1888888888888888888888884444444444444444444444444444443333333333333333332222222222222222288888888888888884444444444444444444444444443333d888888888888888866666";
//		System.out.println("原字符串：" + str);
//		System.out.println("原长度：" + str.length());
//		String compress = GZIPUtils.compress(str);
//		System.out.println("压缩后字符串：" + compress);
//		System.out.println("压缩后字符串长度：" + compress.length());
//		String string = GZIPUtils.uncompress(compress);
//		System.out.println("解压缩后字符串：" + string);
//		System.out.println("解压缩后字符串：" + str);
//	}
}