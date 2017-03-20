/*
 * Copyright 2015-2017 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.jupiter.engine.extension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.InvocationIndex;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

/**
 * Integration tests for {@link RepeatedTest}.
 *
 * @since 5.0
 */
class RepeatedTestTests {

	private static int fortyTwo = 0;

	@BeforeEach
	@AfterEach
	void beforeAndAfterEach(TestInfo testInfo, @InvocationIndex int repetition) {
		if (testInfo.getDisplayName().startsWith("repeatedOnce")) {
			assertThat(repetition).isEqualTo(1);
		}
		else if (testInfo.getDisplayName().startsWith("repeatedFortyTwoTimes")) {
			assertThat(repetition).isBetween(1, 42);
		}
	}

	@AfterAll
	static void afterAll() {
		assertEquals(42, fortyTwo);
	}

	@RepeatedTest(-99)
	void negativeRepeatCount(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("negativeRepeatCount(TestInfo) :: repetition 1");
	}

	@RepeatedTest(0)
	void zeroRepeatCount(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("zeroRepeatCount(TestInfo) :: repetition 1");
	}

	@RepeatedTest(1)
	void repeatedOnce(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("repeatedOnce(TestInfo) :: repetition 1");
	}

	@RepeatedTest(42)
	void repeatedFortyTwoTimes(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).startsWith("repeatedFortyTwoTimes(TestInfo) :: repetition ");
		fortyTwo++;
	}

	@RepeatedTest(1)
	void defaultDisplayName(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("defaultDisplayName(TestInfo) :: repetition 1");
	}

	@RepeatedTest(value = 1, name = " \t  ")
	void defaultDisplayNameWithBlankPattern(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("defaultDisplayNameWithBlankPattern(TestInfo) :: repetition 1");
	}

	@RepeatedTest(1)
	@DisplayName("Repeat!")
	void customDisplayName(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("Repeat! :: repetition 1");
	}

	@RepeatedTest(1)
	@DisplayName("   \t ")
	void customDisplayNameWithBlankName(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("customDisplayNameWithBlankName(TestInfo) :: repetition 1");
	}

	@RepeatedTest(value = 1, name = "{displayName}")
	@DisplayName("Repeat!")
	void customDisplayNameWithPatternIncludingDisplayName(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("Repeat!");
	}

	@RepeatedTest(value = 1, name = "#{repetition}")
	@DisplayName("Repeat!")
	void customDisplayNameWithPatternIncludingIndex(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("#1");
	}

	@RepeatedTest(value = 1, name = "Repetition #{repetition} for {displayName}")
	@DisplayName("Repeat!")
	void customDisplayNameWithPatternIncludingDisplayNameAndIndex(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo("Repetition #1 for Repeat!");
	}

	@RepeatedTest(value = 1, name = "Repetition #{repetition} for {displayName}")
	void defaultDisplayNameWithPatternIncludingDisplayNameAndIndex(TestInfo testInfo) {
		assertThat(testInfo.getDisplayName()).isEqualTo(
			"Repetition #1 for defaultDisplayNameWithPatternIncludingDisplayNameAndIndex(TestInfo)");
	}

	@RepeatedTest(value = 5, name = "{displayName}")
	void injectInvocationIndexAsInt(TestInfo testInfo, @InvocationIndex int repetition) {
		assertThat(testInfo.getDisplayName()).isEqualTo("injectInvocationIndexAsInt(TestInfo, int)");
		assertThat(repetition).isBetween(1, 5);
	}

	@RepeatedTest(value = 5, name = "{displayName}")
	void injectInvocationIndexAsInteger(TestInfo testInfo, @InvocationIndex Integer repetition) {
		assertThat(testInfo.getDisplayName()).isEqualTo("injectInvocationIndexAsInteger(TestInfo, Integer)");
		assertThat(repetition).isBetween(1, 5);
	}

}