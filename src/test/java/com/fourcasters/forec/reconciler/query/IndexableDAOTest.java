package com.fourcasters.forec.reconciler.query;

import com.diffblue.deeptestutils.Reflector;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.function.BiFunction;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class IndexableDAOTest {
  @Rule
  public final Timeout globalTimeout = new Timeout(10000);

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void indexSizeInputNullOutputNegative000fe8f2ebd1e80830b2() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputNegative000b0295f591314a2613() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputNegative000fe8f2ebd1e80830b() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputNegative000b0295f591314a2612() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputNegative000b0295f591314a261() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputNegative001fe8f2ebd1e80830b() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize(null));
  }

  @Test
  public void indexSizeInputNullOutputZero000b81372e3c43751b0() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    final TreeMap<Long, Long> treeMap = new TreeMap<Long, Long>();
    hashMap.put(null, treeMap);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(0, indexableDAO.indexSize(null));
  }

  @Test
  public void applyInputZeroZeroOutputZero00061edef2ecb618d89()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
    final BiFunction indexableDAO1 = (BiFunction) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO$1");
    final Class<?> classUnderTest = Reflector.forName("com.fourcasters.forec.reconciler.query.IndexableDAO$1");
    final Method methodUnderTest = classUnderTest.getDeclaredMethod("apply", Reflector.forName("java.lang.Long"),
        Reflector.forName("java.lang.Integer"));
    methodUnderTest.setAccessible(true);
    final Long actual = (Long) methodUnderTest.invoke(indexableDAO1, 0L, 0);
    Assert.assertEquals(new Long(0L), actual);
  }

  @Test
  public void applyInputZeroZeroOutputZero00010de7afec2733f35()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
    final BiFunction indexableDAO2 = (BiFunction) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO$2");
    final Class<?> classUnderTest = Reflector.forName("com.fourcasters.forec.reconciler.query.IndexableDAO$2");
    final Method methodUnderTest = classUnderTest.getDeclaredMethod("apply", Reflector.forName("java.lang.Long"),
        Reflector.forName("java.lang.Integer"));
    methodUnderTest.setAccessible(true);
    final Long actual = (Long) methodUnderTest.invoke(indexableDAO2, 0L, 0);
    Assert.assertEquals(new Long(0L), actual);
  }

  @Test
  public void indexSizeInputNotNullOutputNegative0019e1f176b18f558f0() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put("foo", null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1, indexableDAO.indexSize("foo"));
  }

  @Test
  public void indexSizeInputNotNullOutputZero000cc706409dae2b67e() throws InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    final TreeMap<Long, Long> treeMap = new TreeMap<Long, Long>();
    hashMap.put("foo", treeMap);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(0, indexableDAO.indexSize("foo"));
  }

  @Test
  public void offsetInputNotNullZeroOutputNegative00041a5bca9e9fb25ee() throws IOException, InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1L, indexableDAO.offset("foo", 0L));
  }

  @Test
  public void offsetExactInputNotNullZeroOutputNegative0008383b308d5dde02b()
      throws IOException, InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    Assert.assertEquals(-1L, indexableDAO.offsetExact("foo", 0L));
  }

  @Test
  public void offsetExactInputNotNullZeroNotNullOutputNegative000d628c521e9d94017()
      throws IOException, InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    final BiFunction f = (BiFunction) Reflector.getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO$1");
    Assert.assertEquals(-1L, indexableDAO.offsetExact("foo", 0L, f));
  }

  @Test
  public void offsetInputNotNullZeroNotNullOutputNegative000c36cddeec14bc1e3()
      throws IOException, InvocationTargetException {
    final IndexableDAO indexableDAO = (IndexableDAO) Reflector
        .getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO");
    final HashMap<String, TreeMap<Long, Long>> hashMap = new HashMap<String, TreeMap<Long, Long>>();
    hashMap.put(null, null);
    Reflector.setField(indexableDAO, "indexes", hashMap);
    final BiFunction f = (BiFunction) Reflector.getInstance("com.fourcasters.forec.reconciler.query.IndexableDAO$1");
    Assert.assertEquals(-1L, indexableDAO.offset("foo", 0L, f));
  }
}
