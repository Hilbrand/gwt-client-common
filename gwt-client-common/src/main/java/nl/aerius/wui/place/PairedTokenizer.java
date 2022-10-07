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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tokenizer to work provide a helper to url path with with / delimiters like key1/value1/key2/value2.
 *
 * @param <P> specific place this works on
 */
public abstract class PairedTokenizer<P extends ApplicationPlace> extends ParameteredTokenizer<P> implements PlaceTokenizer<P> {

  @Override
  public final P getPlace(final String token) {
    final P place = createPlace();

    updatePlace(TokenizerUtils.findPathPairs(token), place);
    return place;
  }

  @Override
  public final String getToken(final P place) {
    final Map<String, String> tokens = new LinkedHashMap<String, String>();

    setTokenMap(place, tokens);
    return TokenizerUtils.formatPathPairs(tokens);
  }
}
