/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.wui.place;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.URL;

public final class TokenizerUtils {
  private static final String PATH_DELIMITER = "/";

  private static final String QUERY_DELIMITER = "&";
  private static final String QUERY_EQUALIZER = "=";

  public static final String QUERY_START_DELIMITER = "?";
  public static final String QUERY_START_DELIMITER_PATTERN = "\\" + QUERY_START_DELIMITER;

  public static Map<String, String> find(final String token) {
    final String[] parts = token.split(QUERY_START_DELIMITER_PATTERN, 2);

    final Map<String, String> pairs = findPathPairs(parts[0]);
    final Map<String, String> composites = findQueryPairs(parts[1]);

    final Map<String, String> combined = new HashMap<>(composites);
    // Overwrite with pairs
    combined.putAll(pairs);
    return combined;
  }

  /**
   * @deprecated Use {@link #findQueryPairs(String)}
   */
  @Deprecated
  public static Map<String, String> findComposite(final String token) {
    return findQueryPairs(token);
  }

  public static Map<String, String> findQueryPairs(final String token) {
    return findPairs(token, QUERY_DELIMITER, QUERY_EQUALIZER);
  }

  /**
   * @deprecated Use {@link #findPathPairs(String)}
   */
  @Deprecated
  public static Map<String, String> findPairs(final String token) {
    return findPathPairs(token);
  }

  /**
   *
   * @param token
   * @return
   */
  public static Map<String, String> findPathPairs(final String token) {
    return findPairs(token, PATH_DELIMITER);
  }

  /**
   * @deprecated Use {@link #formatQueryPairs(Map)}
   */
  @Deprecated
  public static final String formatComposite(final Map<String, String> pairs) {
    return formatQueryPairs(pairs);
  }

  public static final String formatQueryPairs(final Map<String, String> pairs) {
    return formatPairs(pairs, QUERY_DELIMITER, QUERY_EQUALIZER);
  }

  /**
   * @deprecated Use {@link #formatPathPairs(Map)}
   */
  @Deprecated
  public static final String formatPairs(final Map<String, String> pairs) {
    return formatPathPairs(pairs);
  }

  public static final String formatPathPairs(final Map<String, String> pairs) {
    return formatPairs(pairs, PATH_DELIMITER, PATH_DELIMITER);
  }

  public static Map<String, String> findPairs(final String token, final String delimiter) {
    final Map<String, String> values = new HashMap<String, String>();

    if (token == null || token.isEmpty()) {
      return values;
    }

    final String[] args = token.split(delimiter);
    for (int i = 0; i < args.length; i += 2) {
      values.put(args[i], args[i + 1]);
    }

    return values;
  }

  public static Map<String, String> findPairs(final String token, final String delimiter, final String equalizer) {
    final Map<String, String> values = new HashMap<String, String>();

    if (token == null || token.isEmpty()) {
      return values;
    }

    final String[] args = token.split(delimiter);
    for (int i = 0; i < args.length; i++) {
      final String[] pair = args[i].split(equalizer, 2);

      final String key = pair[0];
      if (key.isEmpty()) {
        continue;
      }
      final String val = pair.length > 1 ? pair[1] : null;
      values.put(URL.decodeQueryString(key), val);
    }

    return values;
  }

  public static final String formatPairs(final Map<String, String> pairs, final String delimiter, final String equalizer) {
    final StringBuilder sb = new StringBuilder();
    for (final Entry<String, String> entry : pairs.entrySet()) {
      if (entry.getValue() == null) {
        continue;
      }

      sb.append(entry.getKey());
      sb.append(equalizer);
      sb.append(entry.getValue());
      sb.append(delimiter);
    }

    // Remove the last part (delimiter)
    if (sb.length() != 0) {
      sb.setLength(sb.length() - 1);
    }

    return sb.toString();
  }

  public static String format(final Map<String, String> pairs, final Map<String, String> composites) {
    final String pathToken = formatPathPairs(pairs);
    final String queryToken = formatQueryPairs(composites);

    return formatToken(pathToken, queryToken);
  }

  public static String formatToken(final String base) {
    return base;
  }

  public static String formatToken(final String path, final String query) {
    return path + (query == null || query.isEmpty() ? "" : (QUERY_START_DELIMITER + query));
  }
}
