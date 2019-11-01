package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.mockrepositories.MockTournamentRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TournamentServiceTest
{
    private MockTournamentRepository tournamentRepository;
    private TournamentService tournamentService;

    @Before
    public void setUp()
    {
        tournamentRepository = new MockTournamentRepository();
        tournamentService = new TournamentService(tournamentRepository);
    }

    @Test
    public void createTournamentTest()
    {
        System.out.println(this.tournamentService);
        Integer tournament1Id = 1;
        String tournament1Name = "testTournament1";
        String tournament1Description = "tournament for testing 1";
        User tournament1user1 = new User();
        int tournament1NumberOfTeams = 4;

        Integer tournament2Id = 2;
        String tournament2Name = "testTournament1";
        String tournament2Description = "tournament for testing 1";
        int tournament2NumberOfTeams = 4;

        Tournament tournament1In = new Tournament(tournament1Name, tournament1Description,
                                                   tournament1user1, tournament1NumberOfTeams);
        Tournament tournament2In = new Tournament(tournament2Name, tournament2Description, tournament1user1,
                                                  tournament2NumberOfTeams);

        Tournament tournament1out = new Tournament(tournament1Id, tournament1Name, tournament1Description,
                                               tournament1user1, tournament1NumberOfTeams);
        Tournament tournament2out = new Tournament(tournament2Id, tournament2Name, tournament2Description,
                                                   tournament1user1, tournament2NumberOfTeams);


        this.tournamentRepository.setSaveReturnValue(tournament1out);

        Tournament actualTournament1Out = this.tournamentService.createTournament(tournament1Name, tournament1Description, tournament1user1,
                                                tournament1NumberOfTeams);
        assertEquals(tournament1out, actualTournament1Out);

        Tournament actualTournament1In = this.tournamentRepository.getSaveCalledWithParameters();

        assertEquals(tournament1In, actualTournament1In);



        this.tournamentRepository.setSaveReturnValue(tournament2out);

        Tournament actualTournament2 = this.tournamentService.createTournament(tournament2Name, tournament2Description,
                                                                               tournament1user1,
                                                                               tournament2NumberOfTeams);

        assertEquals(tournament2out, actualTournament2);

        Tournament actualTournament2In = this.tournamentRepository.getSaveCalledWithParameters();

        assertEquals(tournament2In, actualTournament2In);
    }
}
