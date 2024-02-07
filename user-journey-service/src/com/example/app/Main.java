package com.example.app;


import com.example.model.Payload;
import com.example.model.User;
import com.example.model.journey.Journey;
import com.example.model.journey.PerpetualJourney;
import com.example.model.journey.TimeBoundJourney;
import com.example.model.stage.IntermediateStage;
import com.example.model.stage.OnboardingStage;
import com.example.model.stage.Stage;
import com.example.model.stage.TerminalStage;
import com.example.service.UserJourneyService;
import com.example.service.UserJourneyServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {

        System.out.println("\nStarting test1-------");

        Journey journey = createPerpetualJourney();
        User user = new User("userA");

        Payload payload = new Payload(journey.getJourneyId(), user.getUserId(),  new HashMap<>());

        UserJourneyService service = new UserJourneyServiceImpl();

        service.createJourney(journey);
        service.updateState(journey.getJourneyId(), true);
        Journey journeyFound = service.getJourney(journey.getJourneyId());
        if (journeyFound == journey) {
            System.out.println("Journey Found");
        }
        service.evaluate(user.getUserId(), payload);

        Payload payload1 = new Payload(journey.getJourneyId(), user.getUserId(), Map.of("Onboarding", "Onboarding" +
                "-done"));
        service.evaluate(user.getUserId(), payload1);

        Stage userStage = service.getCurrentStage(user.getUserId(), journey.getJourneyId());
        if (userStage != null)
            System.out.println("Current userStage: " + userStage.getName());

        boolean isOnboarded = service.isOnboarded(user.getUserId(), journey.getJourneyId());
        System.out.println("isOnboarded: " + isOnboarded);

        System.out.println("Test1 ran successfully!!");
    }

    private static Journey createPerpetualJourney() {
        Journey journey = new PerpetualJourney();

        Stage onboarding = new OnboardingStage("Onboarding");
        Stage stage2 = new IntermediateStage("Intermediate1", List.of(onboarding.getName()));
        Stage stage3 = new IntermediateStage("Intermediate2", List.of(stage2.getName()));
        Stage terminal = new TerminalStage("Terminal", List.of(stage3.getName()));

        onboarding.setNextStage(stage2);
        stage2.setNextStage(stage3);
        stage3.setNextStage(terminal);

        List<Stage> stages = List.of(onboarding, stage2, stage3, terminal);

        journey.setStages(stages);
        return journey;
    }

    public static void test2() {

        System.out.println("\nStarting test2-------");

        Journey journey = createTimeBoundJourney();
        User user = new User("userA");

        Payload payload = new Payload(journey.getJourneyId(), user.getUserId(),  new HashMap<>());

        UserJourneyService service = new UserJourneyServiceImpl();

        service.createJourney(journey);
        service.updateState(journey.getJourneyId(), true);
        Journey journeyFound = service.getJourney(journey.getJourneyId());
        if (journeyFound == journey) {
            System.out.println("Journey Found");
        }
        service.evaluate(user.getUserId(), payload);

        Payload payload1 = new Payload(journey.getJourneyId(), user.getUserId(), Map.of("Onboarding", "Onboarding" +
                "-done"));
        service.evaluate(user.getUserId(), payload1);

        Stage userStage = service.getCurrentStage(user.getUserId(), journey.getJourneyId());
        if (userStage != null)
            System.out.println("Current userStage: " + userStage.getName());

        boolean isOnboarded = service.isOnboarded(user.getUserId(), journey.getJourneyId());
        System.out.println("isOnboarded: " + isOnboarded);

        System.out.println("Test2 ran successfully!!");
    }

    private static Journey createTimeBoundJourney() {
        Date startDate = new Date(System.currentTimeMillis() - 1000000);
        Date endDate = new Date(System.currentTimeMillis() + 10000000);
        Journey journey = new TimeBoundJourney(startDate, endDate);


        Stage onboarding = new OnboardingStage("Onboarding");
        Stage stage2 = new IntermediateStage("Intermediate1", List.of(onboarding.getName()));
        Stage stage3 = new IntermediateStage("Intermediate2", List.of(stage2.getName()));
        Stage terminal = new TerminalStage("Terminal", List.of(stage3.getName()));

        onboarding.setNextStage(stage2);
        stage2.setNextStage(stage3);
        stage3.setNextStage(terminal);

        List<Stage> stages = List.of(onboarding, stage2, stage3, terminal);

        journey.setStages(stages);
        return journey;
    }

}
