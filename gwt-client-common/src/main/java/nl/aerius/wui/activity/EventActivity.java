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
package nl.aerius.wui.activity;

import com.google.web.bindery.event.shared.EventBus;

import nl.aerius.wui.event.HasEventBus;

public abstract class EventActivity<P, V extends View<P>> extends AbstractActivity<P, V> implements HasEventBus {
  private final HasEventBus[] eventChildren;

  protected EventBus eventBus;

  public EventActivity(final V view, final HasEventBus... eventChildren) {
    super(view);

    this.eventChildren = eventChildren;
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;

    if (view instanceof HasEventBus) {
      ((HasEventBus) view).setEventBus(eventBus);
    }

    for (final HasEventBus child : eventChildren) {
      child.setEventBus(eventBus);
    }
  }
}
