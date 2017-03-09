package com.segment.analytics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ValueMapCacheTest {

  private ValueMap.Cache<Traits> traitsCache;
  private Cartographer cartographer;

  @Before
  public void setUp() {
    cartographer = Cartographer.INSTANCE;
    traitsCache =
        new ValueMap.Cache<>(
            RuntimeEnvironment.application, cartographer, "traits-cache-test", "tag", Traits.class);
    traitsCache.delete();
    assertThat(traitsCache.get()).isNullOrEmpty();
  }

  @Test
  public void save() throws Exception {
    Traits traits = new Traits().putValue("foo", "bar");
    traitsCache.set(traits);
    assertThat(traitsCache.get()).isEqualTo(traits);
  }

  @Test
  public void cacheWithSameKeyHasSameValue() throws Exception {
    assertThat(traitsCache.isSet()).isFalse();
    Traits traits = new Traits().putValue("foo", "bar");
    traitsCache.set(traits);

    ValueMap.Cache<Traits> traitsCacheDuplicate =
        new ValueMap.Cache<>(
            RuntimeEnvironment.application, cartographer, "traits-cache-test", "tag", Traits.class);
    assertThat(traitsCacheDuplicate.isSet()).isTrue();
  }
}
