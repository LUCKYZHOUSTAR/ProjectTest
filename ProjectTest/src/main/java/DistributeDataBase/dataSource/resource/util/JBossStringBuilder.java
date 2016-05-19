package DistributeDataBase.dataSource.resource.util;

import java.io.Serializable;

public class JBossStringBuilder
  implements Serializable, CharSequence
{
  private static final long serialVersionUID = 1874946609763446794L;
  protected char[] chars;
  protected int pos;

  public JBossStringBuilder()
  {
    this(16);
  }

  public JBossStringBuilder(int capacity)
  {
    this.chars = new char[capacity];
  }

  public JBossStringBuilder(String string)
  {
    this(string.length() + 16);
    append(string);
  }

  public JBossStringBuilder(CharSequence charSequence)
  {
    this(charSequence.length() + 16);
    append(charSequence);
  }

  public JBossStringBuilder append(Object object) {
    return append(String.valueOf(object));
  }

  public JBossStringBuilder append(String string) {
    if (string == null) {
      string = "null";
    }
    int length = string.length();
    if (length == 0) {
      return this;
    }
    int afterAppend = this.pos + length;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    string.getChars(0, length, this.chars, this.pos);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder append(StringBuffer buffer) {
    if (buffer == null) {
      return append("null");
    }
    int length = buffer.length();
    if (length == 0) {
      return this;
    }
    int afterAppend = this.pos + length;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    buffer.getChars(0, length, this.chars, this.pos);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder append(CharSequence charSequence) {
    if (charSequence == null) {
      return append("null");
    }
    int length = charSequence.length();
    if (length == 0) {
      return this;
    }
    return append(charSequence, 0, charSequence.length());
  }

  public JBossStringBuilder append(CharSequence charSequence, int start, int end) {
    if (charSequence == null) {
      return append("null");
    }
    if ((start < 0) || (end < 0) || (start > end) || (start > charSequence.length())) {
      throw new IndexOutOfBoundsException("Invalid start=" + start + " end=" + end + " length=" + charSequence.length());
    }

    int length = end - start;
    if (length == 0) {
      return this;
    }
    int afterAppend = this.pos + length;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    for (int i = start; i < end; i++)
      this.chars[(this.pos++)] = charSequence.charAt(i);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder append(char[] array) {
    if (array == null) {
      return append("null");
    }
    if (array.length == 0) {
      return this;
    }
    String string = String.valueOf(array);
    return append(string);
  }

  public JBossStringBuilder append(char[] array, int offset, int length) {
    if (array == null) {
      return append("null");
    }
    int arrayLength = array.length;
    if ((offset < 0) || (length < 0) || (offset + length > arrayLength)) {
      throw new IndexOutOfBoundsException("Invalid offset=" + offset + " length=" + length + " array.length=" + arrayLength);
    }

    if ((length == 0) || (arrayLength == 0)) {
      return this;
    }
    String string = String.valueOf(array, offset, length);
    return append(string);
  }

  public JBossStringBuilder append(boolean primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder append(char primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder append(int primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder append(long primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder append(float primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder append(double primitive) {
    String string = String.valueOf(primitive);
    return append(string);
  }

  public JBossStringBuilder delete(int start, int end) {
    if ((start < 0) || (start > this.pos) || (start > end) || (end > this.pos)) {
      throw new IndexOutOfBoundsException("Invalid start=" + start + " end=" + end + " length=" + this.pos);
    }

    if (start == end) {
      return this;
    }
    int removed = end - start;
    System.arraycopy(this.chars, start + removed, this.chars, start, this.pos - end);
    this.pos -= removed;
    return this;
  }

  public JBossStringBuilder deleteCharAt(int index) {
    return delete(index, 1);
  }

  public JBossStringBuilder replace(int start, int end, String string) {
    delete(start, end);
    return insert(start, string);
  }

  public JBossStringBuilder insert(int index, char[] string) {
    return insert(index, string, 0, string.length);
  }

  public JBossStringBuilder insert(int index, char[] string, int offset, int len) {
    int stringLength = string.length;
    if ((index < 0) || (index > this.pos) || (offset < 0) || (len < 0) || (offset + len > string.length)) {
      throw new IndexOutOfBoundsException("Invalid index=" + index + " offset=" + offset + " len=" + len + " string.length=" + stringLength + " length=" + this.pos);
    }

    if (len == 0) {
      return this;
    }
    int afterAppend = this.pos + len;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    System.arraycopy(this.chars, index, this.chars, index + stringLength, this.pos - index);
    System.arraycopy(string, offset, this.chars, index, len);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder insert(int offset, Object object) {
    if (object == null) {
      return insert(offset, "null");
    }
    return insert(offset, String.valueOf(object));
  }

  public JBossStringBuilder insert(int offset, String string) {
    if ((offset < 0) || (offset > this.pos)) {
      throw new IndexOutOfBoundsException("Invalid offset=" + offset + " length=" + this.pos);
    }
    if (string == null) {
      string = "null";
    }
    int stringLength = string.length();

    int afterAppend = this.pos + stringLength;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    System.arraycopy(this.chars, offset, this.chars, offset + stringLength, this.pos - offset);
    string.getChars(0, stringLength, this.chars, offset);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder insert(int offset, CharSequence charSequence) {
    if (charSequence == null) {
      return insert(offset, "null");
    }
    return insert(offset, charSequence, 0, charSequence.length());
  }

  public JBossStringBuilder insert(int offset, CharSequence charSequence, int start, int end) {
    if (charSequence == null) {
      charSequence = "null";
    }
    int sequenceLength = charSequence.length();
    if ((offset < 0) || (offset > this.pos) || (start < 0) || (end < 0) || (start > sequenceLength) || (end > sequenceLength) || (start > end))
    {
      throw new IndexOutOfBoundsException("Invalid offset=" + offset + " start=" + start + " end=" + end + " sequence.length()=" + sequenceLength + " length=" + this.pos);
    }

    int len = end - start;
    if (len == 0) {
      return this;
    }
    int afterAppend = this.pos + len;
    if (afterAppend > this.chars.length) {
      expandCapacity(afterAppend);
    }
    System.arraycopy(this.chars, offset, this.chars, offset + sequenceLength, this.pos - offset);
    for (int i = start; i < end; i++)
      this.chars[(offset++)] = charSequence.charAt(i);
    this.pos = afterAppend;
    return this;
  }

  public JBossStringBuilder insert(int offset, boolean primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public JBossStringBuilder insert(int offset, char primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public JBossStringBuilder insert(int offset, int primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public JBossStringBuilder insert(int offset, long primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public JBossStringBuilder insert(int offset, float primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public JBossStringBuilder insert(int offset, double primitive) {
    return insert(offset, String.valueOf(primitive));
  }

  public int indexOf(String string) {
    return indexOf(string, 0);
  }

  public int indexOf(String string, int fromIndex) {
    return toString().indexOf(string, fromIndex);
  }

  public int lastIndexOf(String string) {
    return lastIndexOf(string, 0);
  }

  public int lastIndexOf(String string, int fromIndex) {
    return toString().lastIndexOf(string, fromIndex);
  }

  public JBossStringBuilder reverse() {
    throw new NotImplementedException("FIXME: NYI");
  }

  public String toString() {
    return new String(this.chars, 0, this.pos);
  }

  public int length() {
    return this.pos;
  }

  public int capacity() {
    return this.chars.length;
  }

  public void ensureCapacity(int minimum) {
    if ((minimum < 0) || (minimum < this.chars.length))
      return;
    expandCapacity(minimum);
  }

  public void trimToSize() {
    char[] trimmed = new char[this.pos];
    System.arraycopy(this.chars, 0, trimmed, 0, this.pos);
    this.chars = trimmed;
  }

  public void setLength(int newLength) {
    throw new NotImplementedException("FIXME: NYI");
  }

  public char charAt(int index) {
    return this.chars[index];
  }

  public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
    if ((srcBegin < 0) || (dstBegin < 0) || (srcBegin > srcEnd) || (srcEnd > this.pos) || (dstBegin + srcEnd - srcBegin > dst.length))
    {
      throw new IndexOutOfBoundsException("Invalid srcBegin=" + srcBegin + " srcEnd=" + srcEnd + " dstBegin=" + dstBegin + " dst.length=" + dst.length + " length=" + this.pos);
    }

    int len = srcEnd - srcBegin;
    if (len == 0) {
      return;
    }
    System.arraycopy(this.chars, srcBegin, dst, dstBegin, len);
  }

  public void setCharAt(int index, char ch) {
    if ((index < 0) || (index > this.pos)) {
      throw new IndexOutOfBoundsException("Invalid index=" + index + " length=" + this.pos);
    }
    this.chars[index] = ch;
  }

  public String substring(int start) {
    return substring(start, this.pos);
  }

  public CharSequence subSequence(int start, int end) {
    return substring(start, end);
  }

  public String substring(int start, int end) {
    if ((start < 0) || (end < 0) || (start > end) || (end > this.pos)) {
      throw new IndexOutOfBoundsException("Invalid start=" + start + " end=" + end + " length=" + this.pos);
    }

    return new String(this.chars, start, end - start);
  }

  protected void expandCapacity(int minimum)
  {
    int newSize = this.chars.length * 2;
    if (minimum > newSize) {
      newSize = minimum;
    }
    char[] newChars = new char[newSize];
    System.arraycopy(this.chars, 0, newChars, 0, this.pos);
    this.chars = newChars;
  }
}