/*! hs-d4 by xuebj 2014-11-10 */
!function ($w) {
    function BarrettMu_modulo(x) {
        var $dmath = RSAUtils, q1 = $dmath.biDivideByRadixPower(x, this.k - 1), q2 = $dmath.biMultiply(q1, this.mu),
            q3 = $dmath.biDivideByRadixPower(q2, this.k + 1), r1 = $dmath.biModuloByRadixPower(x, this.k + 1),
            r2term = $dmath.biMultiply(q3, this.modulus), r2 = $dmath.biModuloByRadixPower(r2term, this.k + 1),
            r = $dmath.biSubtract(r1, r2);
        r.isNeg && (r = $dmath.biAdd(r, this.bkplus1));
        for (var rgtem = $dmath.biCompare(r, this.modulus) >= 0; rgtem;) r = $dmath.biSubtract(r, this.modulus), rgtem = $dmath.biCompare(r, this.modulus) >= 0;
        return r
    }

    function BarrettMu_multiplyMod(x, y) {
        var xy = RSAUtils.biMultiply(x, y);
        return this.modulo(xy)
    }

    function BarrettMu_powMod(x, y) {
        var result = new BigInt;
        result.digits[0] = 1;
        for (var a = x, k = y; ;) {
            if (0 != (1 & k.digits[0]) && (result = this.multiplyMod(result, a)), k = RSAUtils.biShiftRight(k, 1), 0 == k.digits[0] && 0 == RSAUtils.biHighIndex(k)) break;
            a = this.multiplyMod(a, a)
        }
        return result
    }

    if ("undefined" == typeof $w.RSAUtils) var RSAUtils = $w.RSAUtils = {};
    var maxDigits, ZERO_ARRAY, bigZero, bigOne, biRadixBits = 16, bitsPerDigit = biRadixBits, biRadix = 65536,
        biHalfRadix = biRadix >>> 1, biRadixSquared = biRadix * biRadix, maxDigitVal = biRadix - 1,
        BigInt = $w.BigInt = function (flag) {
            this.digits = "boolean" == typeof flag && 1 == flag ? null : ZERO_ARRAY.slice(0), this.isNeg = !1
        };
    RSAUtils.setMaxDigits = function (value) {
        maxDigits = value, ZERO_ARRAY = new Array(maxDigits);
        for (var iza = 0; iza < ZERO_ARRAY.length; iza++) ZERO_ARRAY[iza] = 0;
        bigZero = new BigInt, bigOne = new BigInt, bigOne.digits[0] = 1
    }, RSAUtils.setMaxDigits(20);
    var dpl10 = 15;
    RSAUtils.biFromNumber = function (i) {
        var result = new BigInt;
        result.isNeg = 0 > i, i = Math.abs(i);
        for (var j = 0; i > 0;) result.digits[j++] = i & maxDigitVal, i = Math.floor(i / biRadix);
        return result
    };
    var lr10 = RSAUtils.biFromNumber(1e15);
    RSAUtils.biFromDecimal = function (s) {
        for (var result, isNeg = "-" == s.charAt(0), i = isNeg ? 1 : 0; i < s.length && "0" == s.charAt(i);) ++i;
        if (i == s.length) result = new BigInt; else {
            var digitCount = s.length - i, fgl = digitCount % dpl10;
            for (0 == fgl && (fgl = dpl10), result = RSAUtils.biFromNumber(Number(s.substr(i, fgl))), i += fgl; i < s.length;) result = RSAUtils.biAdd(RSAUtils.biMultiply(result, lr10), RSAUtils.biFromNumber(Number(s.substr(i, dpl10)))), i += dpl10;
            result.isNeg = isNeg
        }
        return result
    }, RSAUtils.biCopy = function (bi) {
        var result = new BigInt(!0);
        return result.digits = bi.digits.slice(0), result.isNeg = bi.isNeg, result
    }, RSAUtils.reverseStr = function (s) {
        for (var result = "", i = s.length - 1; i > -1; --i) result += s.charAt(i);
        return result
    };
    var hexatrigesimalToChar = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    RSAUtils.biToString = function (x, radix) {
        var b = new BigInt;
        b.digits[0] = radix;
        for (var qr = RSAUtils.biDivideModulo(x, b), result = hexatrigesimalToChar[qr[1].digits[0]]; 1 == RSAUtils.biCompare(qr[0], bigZero);) qr = RSAUtils.biDivideModulo(qr[0], b), digit = qr[1].digits[0], result += hexatrigesimalToChar[qr[1].digits[0]];
        return (x.isNeg ? "-" : "") + RSAUtils.reverseStr(result)
    }, RSAUtils.biToDecimal = function (x) {
        var b = new BigInt;
        b.digits[0] = 10;
        for (var qr = RSAUtils.biDivideModulo(x, b), result = String(qr[1].digits[0]); 1 == RSAUtils.biCompare(qr[0], bigZero);) qr = RSAUtils.biDivideModulo(qr[0], b), result += String(qr[1].digits[0]);
        return (x.isNeg ? "-" : "") + RSAUtils.reverseStr(result)
    };
    var hexToChar = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"];
    RSAUtils.digitToHex = function (n) {
        var mask = 15, result = "";
        for (i = 0; 4 > i; ++i) result += hexToChar[n & mask], n >>>= 4;
        return RSAUtils.reverseStr(result)
    }, RSAUtils.biToHex = function (x) {
        for (var result = "", i = (RSAUtils.biHighIndex(x), RSAUtils.biHighIndex(x)); i > -1; --i) result += RSAUtils.digitToHex(x.digits[i]);
        return result
    }, RSAUtils.charToHex = function (c) {
        var result, ZERO = 48, NINE = ZERO + 9, littleA = 97, littleZ = littleA + 25, bigA = 65, bigZ = 90;
        return result = c >= ZERO && NINE >= c ? c - ZERO : c >= bigA && bigZ >= c ? 10 + c - bigA : c >= littleA && littleZ >= c ? 10 + c - littleA : 0
    }, RSAUtils.hexToDigit = function (s) {
        for (var result = 0, sl = Math.min(s.length, 4), i = 0; sl > i; ++i) result <<= 4, result |= RSAUtils.charToHex(s.charCodeAt(i));
        return result
    }, RSAUtils.biFromHex = function (s) {
        for (var result = new BigInt, sl = s.length, i = sl, j = 0; i > 0; i -= 4, ++j) result.digits[j] = RSAUtils.hexToDigit(s.substr(Math.max(i - 4, 0), Math.min(i, 4)));
        return result
    }, RSAUtils.biFromString = function (s, radix) {
        var isNeg = "-" == s.charAt(0), istop = isNeg ? 1 : 0, result = new BigInt, place = new BigInt;
        place.digits[0] = 1;
        for (var i = s.length - 1; i >= istop; i--) {
            var c = s.charCodeAt(i), digit = RSAUtils.charToHex(c), biDigit = RSAUtils.biMultiplyDigit(place, digit);
            result = RSAUtils.biAdd(result, biDigit), place = RSAUtils.biMultiplyDigit(place, radix)
        }
        return result.isNeg = isNeg, result
    }, RSAUtils.biDump = function (b) {
        return (b.isNeg ? "-" : "") + b.digits.join(" ")
    }, RSAUtils.biAdd = function (x, y) {
        var result;
        if (x.isNeg != y.isNeg) y.isNeg = !y.isNeg, result = RSAUtils.biSubtract(x, y), y.isNeg = !y.isNeg; else {
            result = new BigInt;
            for (var n, c = 0, i = 0; i < x.digits.length; ++i) n = x.digits[i] + y.digits[i] + c, result.digits[i] = n % biRadix, c = Number(n >= biRadix);
            result.isNeg = x.isNeg
        }
        return result
    }, RSAUtils.biSubtract = function (x, y) {
        var result;
        if (x.isNeg != y.isNeg) y.isNeg = !y.isNeg, result = RSAUtils.biAdd(x, y), y.isNeg = !y.isNeg; else {
            result = new BigInt;
            var n, c;
            c = 0;
            for (var i = 0; i < x.digits.length; ++i) n = x.digits[i] - y.digits[i] + c, result.digits[i] = n % biRadix, result.digits[i] < 0 && (result.digits[i] += biRadix), c = 0 - Number(0 > n);
            if (-1 == c) {
                c = 0;
                for (var i = 0; i < x.digits.length; ++i) n = 0 - result.digits[i] + c, result.digits[i] = n % biRadix, result.digits[i] < 0 && (result.digits[i] += biRadix), c = 0 - Number(0 > n);
                result.isNeg = !x.isNeg
            } else result.isNeg = x.isNeg
        }
        return result
    }, RSAUtils.biHighIndex = function (x) {
        for (var result = x.digits.length - 1; result > 0 && 0 == x.digits[result];) --result;
        return result
    }, RSAUtils.biNumBits = function (x) {
        var result, n = RSAUtils.biHighIndex(x), d = x.digits[n], m = (n + 1) * bitsPerDigit;
        for (result = m; result > m - bitsPerDigit && 0 == (32768 & d); --result) d <<= 1;
        return result
    }, RSAUtils.biMultiply = function (x, y) {
        for (var c, uv, k, result = new BigInt, n = RSAUtils.biHighIndex(x), t = RSAUtils.biHighIndex(y), i = 0; t >= i; ++i) {
            for (c = 0, k = i, j = 0; n >= j; ++j, ++k) uv = result.digits[k] + x.digits[j] * y.digits[i] + c, result.digits[k] = uv & maxDigitVal, c = uv >>> biRadixBits;
            result.digits[i + n + 1] = c
        }
        return result.isNeg = x.isNeg != y.isNeg, result
    }, RSAUtils.biMultiplyDigit = function (x, y) {
        var n, c, uv;
        result = new BigInt, n = RSAUtils.biHighIndex(x), c = 0;
        for (var j = 0; n >= j; ++j) uv = result.digits[j] + x.digits[j] * y + c, result.digits[j] = uv & maxDigitVal, c = uv >>> biRadixBits;
        return result.digits[1 + n] = c, result
    }, RSAUtils.arrayCopy = function (src, srcStart, dest, destStart, n) {
        for (var m = Math.min(srcStart + n, src.length), i = srcStart, j = destStart; m > i; ++i, ++j) dest[j] = src[i]
    };
    var highBitMasks = [0, 32768, 49152, 57344, 61440, 63488, 64512, 65024, 65280, 65408, 65472, 65504, 65520, 65528, 65532, 65534, 65535];
    RSAUtils.biShiftLeft = function (x, n) {
        var digitCount = Math.floor(n / bitsPerDigit), result = new BigInt;
        RSAUtils.arrayCopy(x.digits, 0, result.digits, digitCount, result.digits.length - digitCount);
        for (var bits = n % bitsPerDigit, rightBits = bitsPerDigit - bits, i = result.digits.length - 1, i1 = i - 1; i > 0; --i, --i1) result.digits[i] = result.digits[i] << bits & maxDigitVal | (result.digits[i1] & highBitMasks[bits]) >>> rightBits;
        return result.digits[0] = result.digits[i] << bits & maxDigitVal, result.isNeg = x.isNeg, result
    };
    var lowBitMasks = [0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535];
    RSAUtils.biShiftRight = function (x, n) {
        var digitCount = Math.floor(n / bitsPerDigit), result = new BigInt;
        RSAUtils.arrayCopy(x.digits, digitCount, result.digits, 0, x.digits.length - digitCount);
        for (var bits = n % bitsPerDigit, leftBits = bitsPerDigit - bits, i = 0, i1 = i + 1; i < result.digits.length - 1; ++i, ++i1) result.digits[i] = result.digits[i] >>> bits | (result.digits[i1] & lowBitMasks[bits]) << leftBits;
        return result.digits[result.digits.length - 1] >>>= bits, result.isNeg = x.isNeg, result
    }, RSAUtils.biMultiplyByRadixPower = function (x, n) {
        var result = new BigInt;
        return RSAUtils.arrayCopy(x.digits, 0, result.digits, n, result.digits.length - n), result
    }, RSAUtils.biDivideByRadixPower = function (x, n) {
        var result = new BigInt;
        return RSAUtils.arrayCopy(x.digits, n, result.digits, 0, result.digits.length - n), result
    }, RSAUtils.biModuloByRadixPower = function (x, n) {
        var result = new BigInt;
        return RSAUtils.arrayCopy(x.digits, 0, result.digits, 0, n), result
    }, RSAUtils.biCompare = function (x, y) {
        if (x.isNeg != y.isNeg) return 1 - 2 * Number(x.isNeg);
        for (var i = x.digits.length - 1; i >= 0; --i) if (x.digits[i] != y.digits[i]) return x.isNeg ? 1 - 2 * Number(x.digits[i] > y.digits[i]) : 1 - 2 * Number(x.digits[i] < y.digits[i]);
        return 0
    }, RSAUtils.biDivideModulo = function (x, y) {
        var q, r, nb = RSAUtils.biNumBits(x), tb = RSAUtils.biNumBits(y), origYIsNeg = y.isNeg;
        if (tb > nb) return x.isNeg ? (q = RSAUtils.biCopy(bigOne), q.isNeg = !y.isNeg, x.isNeg = !1, y.isNeg = !1, r = biSubtract(y, x), x.isNeg = !0, y.isNeg = origYIsNeg) : (q = new BigInt, r = RSAUtils.biCopy(x)), [q, r];
        q = new BigInt, r = x;
        for (var t = Math.ceil(tb / bitsPerDigit) - 1, lambda = 0; y.digits[t] < biHalfRadix;) y = RSAUtils.biShiftLeft(y, 1), ++lambda, ++tb, t = Math.ceil(tb / bitsPerDigit) - 1;
        r = RSAUtils.biShiftLeft(r, lambda), nb += lambda;
        for (var n = Math.ceil(nb / bitsPerDigit) - 1, b = RSAUtils.biMultiplyByRadixPower(y, n - t); -1 != RSAUtils.biCompare(r, b);) ++q.digits[n - t], r = RSAUtils.biSubtract(r, b);
        for (var i = n; i > t; --i) {
            var ri = i >= r.digits.length ? 0 : r.digits[i], ri1 = i - 1 >= r.digits.length ? 0 : r.digits[i - 1],
                ri2 = i - 2 >= r.digits.length ? 0 : r.digits[i - 2], yt = t >= y.digits.length ? 0 : y.digits[t],
                yt1 = t - 1 >= y.digits.length ? 0 : y.digits[t - 1];
            q.digits[i - t - 1] = ri == yt ? maxDigitVal : Math.floor((ri * biRadix + ri1) / yt);
            for (var c1 = q.digits[i - t - 1] * (yt * biRadix + yt1), c2 = ri * biRadixSquared + (ri1 * biRadix + ri2); c1 > c2;) --q.digits[i - t - 1], c1 = q.digits[i - t - 1] * (yt * biRadix | yt1), c2 = ri * biRadix * biRadix + (ri1 * biRadix + ri2);
            b = RSAUtils.biMultiplyByRadixPower(y, i - t - 1), r = RSAUtils.biSubtract(r, RSAUtils.biMultiplyDigit(b, q.digits[i - t - 1])), r.isNeg && (r = RSAUtils.biAdd(r, b), --q.digits[i - t - 1])
        }
        return r = RSAUtils.biShiftRight(r, lambda), q.isNeg = x.isNeg != origYIsNeg, x.isNeg && (q = origYIsNeg ? RSAUtils.biAdd(q, bigOne) : RSAUtils.biSubtract(q, bigOne), y = RSAUtils.biShiftRight(y, lambda), r = RSAUtils.biSubtract(y, r)), 0 == r.digits[0] && 0 == RSAUtils.biHighIndex(r) && (r.isNeg = !1), [q, r]
    }, RSAUtils.biDivide = function (x, y) {
        return RSAUtils.biDivideModulo(x, y)[0]
    }, RSAUtils.biModulo = function (x, y) {
        return RSAUtils.biDivideModulo(x, y)[1]
    }, RSAUtils.biMultiplyMod = function (x, y, m) {
        return RSAUtils.biModulo(RSAUtils.biMultiply(x, y), m)
    }, RSAUtils.biPow = function (x, y) {
        for (var result = bigOne, a = x; ;) {
            if (0 != (1 & y) && (result = RSAUtils.biMultiply(result, a)), y >>= 1, 0 == y) break;
            a = RSAUtils.biMultiply(a, a)
        }
        return result
    }, RSAUtils.biPowMod = function (x, y, m) {
        for (var result = bigOne, a = x, k = y; ;) {
            if (0 != (1 & k.digits[0]) && (result = RSAUtils.biMultiplyMod(result, a, m)), k = RSAUtils.biShiftRight(k, 1), 0 == k.digits[0] && 0 == RSAUtils.biHighIndex(k)) break;
            a = RSAUtils.biMultiplyMod(a, a, m)
        }
        return result
    }, $w.BarrettMu = function (m) {
        this.modulus = RSAUtils.biCopy(m), this.k = RSAUtils.biHighIndex(this.modulus) + 1;
        var b2k = new BigInt;
        b2k.digits[2 * this.k] = 1, this.mu = RSAUtils.biDivide(b2k, this.modulus), this.bkplus1 = new BigInt, this.bkplus1.digits[this.k + 1] = 1, this.modulo = BarrettMu_modulo, this.multiplyMod = BarrettMu_multiplyMod, this.powMod = BarrettMu_powMod
    };
    var RSAKeyPair = function (encryptionExponent, decryptionExponent, modulus) {
        var $dmath = RSAUtils;
        this.e = $dmath.biFromHex(encryptionExponent), this.d = $dmath.biFromHex(decryptionExponent), this.m = $dmath.biFromHex(modulus), this.chunkSize = 2 * $dmath.biHighIndex(this.m), this.radix = 16, this.barrett = new $w.BarrettMu(this.m)
    };
    RSAUtils.getKeyPair = function (encryptionExponent, decryptionExponent, modulus) {
        return new RSAKeyPair(encryptionExponent, decryptionExponent, modulus)
    }, "undefined" == typeof $w.twoDigit && ($w.twoDigit = function (n) {
        return (10 > n ? "0" : "") + String(n)
    }), RSAUtils.encryptedString = function (key, s) {
        for (var a = [], sl = s.length, i = 0; sl > i;) a[i] = s.charCodeAt(i), i++;
        for (; a.length % key.chunkSize != 0;) a[i++] = 0;
        var j, k, block, al = a.length, result = "";
        for (i = 0; al > i; i += key.chunkSize) {
            for (block = new BigInt, j = 0, k = i; k < i + key.chunkSize; ++j) block.digits[j] = a[k++], block.digits[j] += a[k++] << 8;
            var crypt = key.barrett.powMod(block, key.e),
                text = 16 == key.radix ? RSAUtils.biToHex(crypt) : RSAUtils.biToString(crypt, key.radix);
            result += text + " "
        }
        return result.substring(0, result.length - 1)
    }, RSAUtils.decryptedString = function (key, s) {
        var i, j, block, blocks = s.split(" "), result = "";
        for (i = 0; i < blocks.length; ++i) {
            var bi;
            for (bi = 16 == key.radix ? RSAUtils.biFromHex(blocks[i]) : RSAUtils.biFromString(blocks[i], key.radix), block = key.barrett.powMod(bi, key.d), j = 0; j <= RSAUtils.biHighIndex(block); ++j) result += String.fromCharCode(255 & block.digits[j], block.digits[j] >> 8)
        }
        return 0 == result.charCodeAt(result.length - 1) && (result = result.substring(0, result.length - 1)), result
    }, RSAUtils.setMaxDigits(130)
}(window);