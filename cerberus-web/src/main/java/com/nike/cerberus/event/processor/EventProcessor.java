/*
 * Copyright (c) 2017 Nike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.event.processor;

import com.nike.cerberus.event.Event;

/**
 * Interface for a Cerberus Event processor
 *
 * <p>Once an implementation of this interface is added the the named 'eventProcessors' list in the
 * CmsGuiceModule it will get registered with the event processor. All events that go through the
 * event processor will get processed by all processors asynchronously.
 *
 * <p>ex: Another useful processor might be one that send all events to a sns topic so that a lambda
 * could process the events.
 */
public interface EventProcessor {

  void process(Event event);

  String getName();
}