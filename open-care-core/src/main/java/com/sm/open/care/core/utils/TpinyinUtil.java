package com.sm.open.care.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

/**
 * @Description: 拼音转换
 */
public class TpinyinUtil {
	private static final Logger logger = Logger.getLogger(TpinyinUtil.class);

	/**
	 * 文件复制功能
	 * 
	 * @param from
	 *            起始文件路径
	 * @param to
	 *            目标文件路径
	 * @return size 文件复制过程中操作的字节数
	 * @throws IOException
	 */
	// 简体中文的编码范围从B0A1（45217）一直到F7FE（63486）
	private static int BEGIN = 45217;
	private static int END = 63486;

	// 按照声母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
	// i, u, v都不做声母, 自定规则跟随前面的字母
	private static char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
			'哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
			'塌', '挖', '昔', '压', '匝', };

	// 二十六个字母区间对应二十七个端点
	// GB2312码汉字区间十进制表示
	private static int[] table = new int[27];

	// 对应首字母区间表
	private static char[] initialtable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			't', 't', 'w', 'x', 'y', 'z', };

	// 初始化
	static {
		for (int i = 0; i < 26; i++) {
			table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
		}
		table[26] = END;// 区间表结尾
	}

	// ------------------------public方法区------------------------
	/**
	 * 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串 最重要的一个方法，思路如下：一个个字符读入、判断、输出
	 */
	public static String cn2py(String SourceStr) throws IOException {
		String result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				char a = SourceStr.charAt(i);
				char[] b = { '坂', '灞', '濞', '璧', '鲅', '碚', '亳' };
				char[] c = { '岑', '重', '禅', '廛', '茌', '朐', '枞', '眙', '阊', '城' };
				char[] d = { '宕', '棣', '岱', '砀', '磴', '坻' };
				char[] f = { '邡', '罘', '芙' };
				char[] g = { '珙', '藁', '莞' };
				char[] h = { '湟', '濠', '潢', '邗', '桦', '骅' };
				char[] j = { '伽', '泾', '筠', '犍', '旌', '郏', '鄄', '莒', '暨', '绛',
						'稷', '迦' };
				char[] k = { '崆', '岢' };
				char[] l = { '岚', '麟', '僳', '泸', '蒗', '阆', '崃', '澧', '耒', '醴',
						'浏', '漯', '栾', '溧', '赉', '蠡', '涞', '蔺' };
				char[] m = { '岷', '勐', '谟', '湄', '汨', '渑', '闵' };
				char[] n = { '讷' };
				char[] o = { '瓯' };
				char[] p = { '郫', '濮', '鄱', '邳' };
				char[] q = { '岐', '麒', '阡', '邛', '蕲', '硚', '淇', '杞', '谯', '鸠',
						'衢', '綦' };
				char[] r = { '榕', '芮' };
				char[] s = { '耆', '鄯', '嵩', '沭', '泗', '濉', '嵊', '睢', '淞', '浉' };
				char[] t = { '洮', '峒', '潼', '覃', '郯' };
				char[] w = { '汶', '婺' };
				char[] x = { '荥', '浠', '猇', '淅', '莘', '浔', '芗', '歙', '盱', '岫',
						'隰', '陉' };
				char[] y = { '晏', '沅', '蓥', '邕', '禺', '驿', '鄢', '偃', '郓', '兖',
						'峄', '弋', '埇', '颍', '琊', '黟', '猗', '源', '鄞', '郾', '攸' };
				char[] z = { '藏', '梓' };

				// char b='浉';
				// char c='坻';
				// char d='陉';
				// char e='藁';
				// char f='涞';
				// char g='蠡';
				// char h='栾';
				// char j='骅';
				// char k='猗';char l='稷';char m='绛';char n='芮';char o='岢';char
				// p='岚';char q='隰';char x='洮';
				// char a1='磴';char a2='赉';char a3='岫';char a4='鲅';char
				// a5='桦';char a7='讷';char a8='闵';
				// char b1='溧';char b2='睢';char b3='阊';
				boolean istrue = false;
				handResult(a, b, 'b', istrue, result);
				handResult(a, c, 'c', istrue, result);
				handResult(a, d, 'd', istrue, result);
				handResult(a, f, 'f', istrue, result);
				handResult(a, g, 'g', istrue, result);
				handResult(a, h, 'h', istrue, result);
				handResult(a, j, 'j', istrue, result);
				handResult(a, k, 'k', istrue, result);
				handResult(a, l, 'l', istrue, result);
				handResult(a, m, 'm', istrue, result);
				handResult(a, n, 'n', istrue, result);
				handResult(a, o, 'o', istrue, result);
				handResult(a, p, 'p', istrue, result);
				handResult(a, q, 'q', istrue, result);
				handResult(a, r, 'r', istrue, result);
				handResult(a, s, 's', istrue, result);
				handResult(a, t, 't', istrue, result);
				handResult(a, w, 'w', istrue, result);
				handResult(a, y, 'y', istrue, result);
				handResult(a, x, 'x', istrue, result);
				handResult(a, z, 'z', istrue, result);
				// if(a==b ||a==b2){
				// result = result+'s';
				// }else if(a==c || a==a1){
				// result = result+'d';
				// }else if(a==d || a==q || a==a3){
				// result = result+'x';
				// }else if(a==e){
				// result = result+'g';
				// }else if(a==f || a==g || a==h || a==p ||a==a2 || a==b1){
				// result = result+'l';
				// }else if(a==j || a==a5){
				// result = result+'h';
				// }else if(a==k){
				// result = result+'y';
				// }else if(a==l || a==m){
				// result = result+'j';
				// }else if(a==n){
				// result = result+'r';
				// }else if(a==o){
				// result = result+'k';
				// }else if(a==x){
				// result = result+'t';
				// }else if(a==a4){
				// result = result+'b';
				// }else if(a==a7){
				// result = result+'n';
				// }else if(a==a8){
				// result = result+'m';
				// }else if(a==b3){
				// result = result+'c';
				// }else
				if (!istrue) {
					result += Char2Initial(SourceStr.charAt(i));
				}
			}
		} catch (Exception e) {
			result = "";
		}
		return result.toUpperCase(Locale.ENGLISH);
	}
	
	private static void handResult(char a, char[] chs, char ch, boolean istrue, String result){
		for (int ai = 0; ai < chs.length; ai++) {
			if (a == chs[ai]) {
				istrue = true;
				result = result + ch;
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("TpinyinUtil.handResult{a:"+a+",chs:"+JSON.toJSONString(chs)+",ch:"+ch+",istrue:"+istrue+",result:"+result+"}");
		}
	}

	// ------------------------private方法区------------------------
	/**
	 * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0'
	 *
	 */
	private static char Char2Initial(char ch) {
		// 对英文字母的处理：小写字母转换为大写，大写的直接返回
		if (ch >= 'a' && ch <= 'z') {
			return (char) (ch - 'a' + 'A');
		}
		if (ch >= 'A' && ch <= 'Z') {
			return ch;
		}

		// 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
		// 若不是，则直接返回。
		// 若是，则在码表内的进行判断。
		int gb = gbValue(ch);// 汉字转换首字母

		if ((gb < BEGIN) || (gb > END)) {
			// 在码表区间之前，直接返回
			return ch;
		}
		int i=0;
		handI(i, gb);
		return initialtable[i]; // 在码表区间中，返回首字母
	}
	
	private static void handI(int i, int gb){
		for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
			if ((gb >= table[i]) && (gb < table[i + 1])) {
				break;
			}
		}
		if (gb == END) {// 补上GB2312区间最右端
			i = 25;
		}
	}

	/**
	 * 取出汉字的编码 cn 汉字
	 */
	private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
		String str = "";
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2) {
				return 0;
			}
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

}
