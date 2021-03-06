package com.f2prateek.rx.preferences2;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import static com.f2prateek.rx.preferences2.Preconditions.checkNotNull;

final class ConverterAdapter<T> implements RealPreference.Adapter<T> {
  private final Preference.Converter<T> converter;

  ConverterAdapter(Preference.Converter<T> converter) {
    this.converter = converter;
  }

  @Override public T get(@NonNull String key, @NonNull SharedPreferences preferences) {
    String serialized = preferences.getString(key, null);
    assert serialized != null; // Not called unless key is present.
    T value = converter.deserialize(serialized);
    checkNotNull(value, "Deserialized value must not be null from string: " + serialized);
    return value;
  }

  @Override
  public void set(@NonNull String key, @NonNull T value, @NonNull SharedPreferences.Editor editor) {
    String serialized = converter.serialize(value);
    checkNotNull(serialized, "Serialized string must not be null from value: " + value);
    editor.putString(key, serialized);
  }
}
