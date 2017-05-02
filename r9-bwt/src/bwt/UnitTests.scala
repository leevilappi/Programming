
package bwt

import org.junit.Test
import org.junit.Assert._

class UnitTests {
  @Test def testN123() {
    assertTrue("testN123() failed", transform("banana|") == "bnn|aaa")
  }
  @Test def testN124() {
    assertTrue("testN124() failed", transform("alpakkajakkasakkavakkanakka|") == "kpvjsn|kkkkakkkkkaaaaaaalaaa")
  }
  @Test def testN125() {
    assertTrue("testN125() failed", transform("donau|") == "n|odau")
  }
  @Test def testN126() {
    assertTrue("testN126() failed", transform("nilenil|") == "lnnii|el")
  }
  @Test def testN127() {
    assertTrue("testN127() failed", transform("danube|") == "du|bane")
  }
  @Test def testN128() {
    assertTrue("testN128() failed", transform("aavankaavanjaavanlaavanhaavan|") == "lk|jhvvvvvaaaaannnnaaaaaaaaaan")
  }
  @Test def testN129() {
    assertTrue("testN129() failed", transform("xyzw|") == "z|xyw")
  }
  @Test def testN130() {
    assertTrue("testN130() failed", transform("abracadabra|") == "|drcraaaabba")
  }
  @Test def testN131() {
    assertTrue("testN131() failed", transform("foobar|") == "bo|ofar")
  }
  @Test def testN132() {
    assertTrue("testN132() failed", transform("mississippi|") == "ssmp|pissiii")
  }
  @Test def testN133() {
    assertTrue("testN133() failed", transform("|") == "|")
  }
  @Test def testN456() {
    assertTrue("testN456() failed", inverse("bnn|aaa") == "banana|")
  }
  @Test def testN457() {
    assertTrue("testN457() failed", inverse("kpvjsn|kkkkakkkkkaaaaaaalaaa") == "alpakkajakkasakkavakkanakka|")
  }
  @Test def testN458() {
    assertTrue("testN458() failed", inverse("n|odau") == "donau|")
  }
  @Test def testN459() {
    assertTrue("testN459() failed", inverse("lnnii|el") == "nilenil|")
  }
  @Test def testN460() {
    assertTrue("testN460() failed", inverse("du|bane") == "danube|")
  }
  @Test def testN461() {
    assertTrue("testN461() failed", inverse("lk|jhvvvvvaaaaannnnaaaaaaaaaan") == "aavankaavanjaavanlaavanhaavan|")
  }
  @Test def testN462() {
    assertTrue("testN462() failed", inverse("z|xyw") == "xyzw|")
  }
  @Test def testN463() {
    assertTrue("testN463() failed", inverse("|drcraaaabba") == "abracadabra|")
  }
  @Test def testN464() {
    assertTrue("testN464() failed", inverse("bo|ofar") == "foobar|")
  }
  @Test def testN465() {
    assertTrue("testN465() failed", inverse("ssmp|pissiii") == "mississippi|")
  }
  @Test def testN466() {
    assertTrue("testN466() failed", inverse("|") == "|")
  }
}

