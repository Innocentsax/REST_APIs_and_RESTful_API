package com.decagon.scorecardapi.serviceImpl;

import com.decagon.scorecardapi.dto.WeeklyScoreDto;
import com.decagon.scorecardapi.dto.DecadevDto;
import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.dto.responsedto.APIResponse;
import com.decagon.scorecardapi.exception.*;
import com.decagon.scorecardapi.exception.CustomException;
import com.decagon.scorecardapi.exception.UserNotFoundException;
import com.decagon.scorecardapi.model.Admin;
import com.decagon.scorecardapi.model.Decadev;
import com.decagon.scorecardapi.model.WeeklyScore;
import com.decagon.scorecardapi.repository.AdminRepository;
import com.decagon.scorecardapi.repository.DecadevRepository;
import com.decagon.scorecardapi.repository.WeeklyScoreRepository;
import com.decagon.scorecardapi.exception.SquadNotFoundException;
import com.decagon.scorecardapi.exception.StackNotFoundException;
import com.decagon.scorecardapi.model.*;
import com.decagon.scorecardapi.repository.*;
import com.decagon.scorecardapi.response.AdminResponse;
import com.decagon.scorecardapi.service.AdminService;
import com.decagon.scorecardapi.service.EmailService;
import com.decagon.scorecardapi.utility.CalculateScores;
import com.decagon.scorecardapi.utility.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final DecadevRepository decadevRepository;
    private final WeeklyScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final PodRepository podRepository;
    private final StackRepository stackRepository;
    private final SquadRepository squadRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final EmailService emailService;

    @Override
    public List<AdminResponse> getAllAdmin() {
        List<Admin> admins = adminRepository.findAll();
        List<AdminResponse> adminResponses = new ArrayList<>();
        if (admins.size() != 0) {
            for (Admin admin : admins) {
                adminResponses.add(new AdminResponse(admin));
            }
        }
        return adminResponses;
    }

    @Override
    public WeeklyScore decadevWeeklyScore(WeeklyScoreDto score, Long id) {
        if((score.getAlgorithmScore() < 0|| score.getAlgorithmScore() > 100.0)
                || (score.getWeeklyTask() < 0 || score.getWeeklyTask() > 100.0)
                || (score.getWeeklyAssessment() < 0 || score.getWeeklyAssessment() > 100.0)
                || (score.getAgileTest() < 0 || score.getAgileTest() >100.0)
                || (score.getQaTest() < 0 || score.getQaTest() > 100.0 )){
            throw new CustomException("Decadev score shouldn't be less than zero(0) or greater than 100 ");
        }

        Decadev dev = decadevRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No Decadev with the ID: " + id));
        if (scoreRepository.findWeeklyScoreByWeekAndDecadev(score.getWeek(), dev) != null) {
            throw new CustomException("Weekly score already populated");
        }
        WeeklyScore devWeeklyScore = new WeeklyScore();
        double result = CalculateScores.weeklyCumulative(score.getWeeklyTask(),
                score.getAlgorithmScore(), score.getQaTest(), score.getAgileTest(), score.getWeeklyAssessment());
        devWeeklyScore.setAlgorithmScore(score.getAlgorithmScore());
        devWeeklyScore.setWeeklyAssessment(score.getWeeklyAssessment());
        devWeeklyScore.setQaTest(score.getQaTest());
        devWeeklyScore.setAgileTest(score.getAgileTest());
        devWeeklyScore.setWeeklyTask(score.getWeeklyTask());
        devWeeklyScore.setWeek(score.getWeek());
        devWeeklyScore.setDecadev(dev);
        devWeeklyScore.setCumulativeScore(result);
        return scoreRepository.save(devWeeklyScore);
    }



    @Override
    public WeeklyScore updateDecadevWeeklyScore(WeeklyScoreDto score, Long devId,Long weekId) {
        Optional<Decadev> dev = decadevRepository.findById(devId);
        if(dev.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        Optional<WeeklyScore> weeklyScore = this.fetchDecadevWeeklyScore(dev.get(),weekId);
        if(weeklyScore.isEmpty()){
            throw new ScoresNotFoundException("weekly scores not found");
        }

        WeeklyScore devWeeklyScore = weeklyScore.get();
        devWeeklyScore.setAlgorithmScore(score.getAlgorithmScore());
        devWeeklyScore.setAgileTest(score.getAgileTest());
        devWeeklyScore.setQaTest(score.getQaTest());
        devWeeklyScore.setWeeklyTask(score.getWeeklyTask());
        devWeeklyScore.setWeek(score.getWeek());
        devWeeklyScore.setWeeklyAssessment(score.getWeeklyAssessment());
        double result = CalculateScores.weeklyCumulative(score.getWeeklyTask(),
                score.getAlgorithmScore(), score.getQaTest(), score.getAgileTest(), score.getWeeklyAssessment());
        devWeeklyScore.setCumulativeScore(result);
        return scoreRepository.save(devWeeklyScore);
    }

    private Optional<WeeklyScore> fetchDecadevWeeklyScore(Decadev decadev, Long weekId) {
        return decadev.getWeeklyScores().stream().filter(weeklyScore -> weeklyScore.getId().equals(weekId)).findFirst();
    }

    public String createDecadev(DecadevDto decadev, Long podId, Long stackId, Long squadId) {
        if (userRepository.findByEmail(decadev.getEmail()).isPresent()) {
            throw new CustomException("User email already exist");
        }
        StringBuilder password = Generator.generatePassword(10);
        Pod pod = podRepository.findById(podId).orElseThrow(() -> new CustomException("Not found"));
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new StackNotFoundException("Not found"));
        Squad squad = squadRepository.findById(squadId).orElseThrow(() -> new SquadNotFoundException("Not found"));
        Decadev dev = new Decadev();
        dev.setFirstName(decadev.getFirstName());
        dev.setLastName(decadev.getLastName());
        dev.setGender(decadev.getGender());
        dev.setEmail(decadev.getEmail());
        dev.setDecadevId(decadev.getDecadevId());
        dev.setRole(decadev.getRole());
        dev.setPassword(passwordEncoder.encode(password));
        dev.setIsAccountActive(true);
        dev.setSquad(squad);
        dev.setStack(stack);
        dev.setPod(pod);
        userRepository.save(dev);
        emailService.sendEmail("ScoreCard login details \n" + "password: " + password + "\n Email: " + dev.getEmail() + "\n",
                "You have been added as a decadev", dev.getEmail());

        return "Dev created succesfully";

    }
    @Override
    public void deleteDecadev(Long decadevId) {
        User decadev =  userRepository.findUserByIdAndRole(decadevId, Role.DEV).orElseThrow(
                () -> new CustomException("User not found"));
        userRepository.delete(decadev);
    }

    @Override
    public APIResponse<Decadev> updateDecadev(DecadevDto decadevDto, Long decadevId,  Long podId, Long stackId, Long squadId ) {
        Decadev decadev = (Decadev) userRepository.findById(decadevId).orElseThrow(() -> new CustomException("Decadev not found"));
        Pod pod = podRepository.findById(podId).orElseThrow(()-> new CustomException("Not found"));
        Stack stack = stackRepository.findById(stackId).orElseThrow(()-> new StackNotFoundException("Not found"));
        Squad squad = squadRepository.findById(squadId).orElseThrow(()-> new SquadNotFoundException("Not found"));
        decadev.setFirstName(decadevDto.getFirstName());
        decadev.setLastName(decadevDto.getLastName());
        decadev.setEmail(decadevDto.getEmail());
        decadev.setSquad(squad);
        decadev.setStack(stack);
        decadev.setPod(pod);
        userRepository.save(decadev);
        return new APIResponse<>(true, "Decadev updated successfully", decadev);

    }
    @Override
    public WeeklyScore getDevWeeklyScore(String week, Long id){
        Decadev dev = decadevRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No Decadev with the ID: " + id));
        return scoreRepository.findWeeklyScoreByWeekAndDecadev(week, dev);
    }


    @Override
    public List<DecadevDto> getAllDecadevsFromAPod(Long podId) {
        Pod pod = podRepository.findById(podId).orElseThrow(()-> new PodNotFoundException("Decadev Not found"));
        return pod.getDecadev().stream().map(DecadevDto::getDecadevFromAPodDto).collect(Collectors.toList());
    }

}
