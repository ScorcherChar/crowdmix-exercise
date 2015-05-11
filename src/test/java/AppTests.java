import com.squiressoftware.crowdmix.App;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.users.InMemoryUserRepository;
import com.squiressoftware.crowdmix.users.UserRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.io.PrintStream;

/*
App behavior tests written based on Scenarios given in specification - see README.md
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTests {

    @Mock
    PrintStream textOutput;
    @Mock
    Clock clock;

    UserRepository userRepository;

    final String POST_COMMAND_ALICE = "Alice -> I love the weather today";
    final String POST_COMMAND_BOB1 = "Bob -> Damn! We lost!";
    final String POST_COMMAND_BOB2 = "Bob -> Good game though.";
    final String POST_COMMAND_CHARLIE = "Charlie -> I'm in New York today! Anyone want to have a coffee?";

    final String READ_COMMAND_ALICE = "Alice";
    final String READ_COMMAND_BOB = "Bob";

    final String EXPECTED_OUTPUT_ALICE = "I love the weather today (5 minutes ago)";
    final String EXPECTED_OUTPUT_BOB1 = "Damn! We lost! (2 minutes ago)";
    final String EXPECTED_OUTPUT_BOB2 = "Good game though. (1 minutes ago)";
    final String EXPECTED_OUTPUT_CHARLIE = "I'm in New York today! Anyone want to have a coffee? (15 seconds ago)";

    final String EXPECTED_WALL_OUTPUT_ALICE = "Alice - I love the weather today (5 minutes ago)";
    final String EXPECTED_WALL_OUTPUT_BOB1 = "Bob - Damn! We lost! (2 minutes ago)";
    final String EXPECTED_WALL_OUTPUT_BOB2 = "Bob - Good game though. (1 minutes ago)";
    final String EXPECTED_WALL_OUTPUT_CHARLIE = "Charlie - I'm in New York today! Anyone want to have a coffee? (15 seconds ago)";

    final String CHARLIE_FOLLOW_ALICE = "Charlie follows Alice";
    final String CHARLIE_FOLLOW_BOB = "Charlie follows Bob ";

    final String CHARLIE_WALL = "Charlie wall";

    DateTime now = new DateTime();

    @Before
    public void setup() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    public void givenAlicePublishesAMessage_whenFeedIsReadFiveMinLater_thenCorrectMessageReturned() {
        postAlice();

        readAlice();

        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_ALICE);
    }

    @Test
    public void givenAliceAndBobPublishesMessages_whenAlicesFeedIsReadFiveMinLater_thenOnlyAlicesMessageReturned() {
        postAlice();
        postBob();

        readAlice();

        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_ALICE);
        verify(textOutput, never()).println(EXPECTED_OUTPUT_BOB2);
        verify(textOutput, never()).println(EXPECTED_OUTPUT_BOB1);
    }

    @Test
    public void givenAliceAndBobPublishesMessages_whenBobsFeedIsReadLater_thenOnlyBobsMessagesReturned() {
        postAlice();
        postBob();

        readBob();

        inOrder(textOutput);
        verify(textOutput, never()).println(EXPECTED_OUTPUT_ALICE);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB2);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB1);
    }

    @Test
    public void givenAliceAndBobPublishesMessages_whenAlicesFeedIsReadThenBobs_thenMessagesOutputInCorrectOrder() {
        postAlice();
        postBob();

        readAlice();
        readBob();

        inOrder(textOutput);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_ALICE);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB2);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB1);
    }

    @Test
    public void givenAliceAndBobPublishesMessages_whenBobsFeedIsReadThenAlice_thenMessagesOutputInCorrectOrder() {
        postAlice();
        postBob();

        readBob();
        readAlice();

        inOrder(textOutput);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB2);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_BOB1);
        verify(textOutput, times(1)).println(EXPECTED_OUTPUT_ALICE);
    }

    @Test
    public void givenCharlieFollowsAlice_whenCharlieRequestsHisWall_thenCharliesAndAlicesMessageOutput() {
        postAlice();
        postCharlie();

        charlieFollowAlice();
        charlieWall();

        inOrder(textOutput);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_CHARLIE);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_ALICE);
    }

    @Test
    public void givenCharlieFollowsAliceNotBob_whenCharlieRequestsHisWall_thenCharliesAndAlicesMessageOutputNotBobs() {
        postAlice();
        postBob();
        postCharlie();

        charlieFollowAlice();
        charlieWall();

        inOrder(textOutput);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_CHARLIE);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_ALICE);
        verify(textOutput, never()).println(EXPECTED_WALL_OUTPUT_BOB2);
        verify(textOutput, never()).println(EXPECTED_WALL_OUTPUT_BOB1);
    }

    @Test
    public void givenCharlieFollowsAliceAndBob_whenCharlieRequestsHisWall_thenAggregatedListShown() {
        postAlice();
        postBob();
        postCharlie();

        charlieFollowAlice();
        charlieFollowsBob();
        charlieWall();

        inOrder(textOutput);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_CHARLIE);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_BOB2);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_BOB1);
        verify(textOutput, times(1)).println(EXPECTED_WALL_OUTPUT_ALICE);
    }

    private void charlieFollowsBob() {
        when(clock.getNow()).thenReturn(now);
        App.run(CHARLIE_FOLLOW_BOB, textOutput, clock, userRepository);
    }


    private void postAlice() {
        when(clock.getNow()).thenReturn(now.minusMinutes(5));
        App.run(POST_COMMAND_ALICE, textOutput, clock, userRepository);
    }

    private void postBob() {
        when(clock.getNow()).thenReturn(now.minusMinutes(2));
        App.run(POST_COMMAND_BOB1, textOutput, clock, userRepository);
        when(clock.getNow()).thenReturn(now.minusMinutes(1));
        App.run(POST_COMMAND_BOB2, textOutput, clock, userRepository);
    }

    private void charlieFollowAlice() {
        when(clock.getNow()).thenReturn(now);
        App.run(CHARLIE_FOLLOW_ALICE, textOutput, clock, userRepository);
    }


    private void charlieWall() {
        when(clock.getNow()).thenReturn(now);
        App.run(CHARLIE_WALL, textOutput, clock, userRepository);
    }

    private void postCharlie() {
        when(clock.getNow()).thenReturn(now.minusSeconds(15));
        App.run(POST_COMMAND_CHARLIE, textOutput, clock, userRepository);
    }

    private void readAlice() {
        when(clock.getNow()).thenReturn(now);
        App.run(READ_COMMAND_ALICE, textOutput, clock, userRepository);
    }

    private void readBob() {
        when(clock.getNow()).thenReturn(now);
        App.run(READ_COMMAND_BOB, textOutput, clock, userRepository);
    }
}
