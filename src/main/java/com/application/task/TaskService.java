package com.application.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.*;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    //https://www.youtube.com/watch?v=K3XWqkseO2A

    @Async
    public void runThread(Task task) {
        getRandomString(task);
        System.out.println(Thread.currentThread().getName());
    }

    public void getRandomString(Task task) {
        Set<String> noDuplicateResult = new TreeSet<>();

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while (noDuplicateResult.size() < task.getLengthLine()) {
            int missingDiference = task.getLengthLine() - noDuplicateResult.size();
            if (currentTime > startTime + 100) {
                throw new IllegalArgumentException("Too Long Length Line");
            }
            for (int i = 0; i < missingDiference; i++) {
                StringBuilder sb = new StringBuilder();
                for (int loop = 0; loop < task.getLength(); loop++) {
                    int index = new Random().nextInt(task.getLetters().length);
                    sb.append(task.getLetters()[index]);
                }
                String resultString = sb.toString();
                noDuplicateResult.add(resultString);
            }
            currentTime = System.currentTimeMillis();
        }

        List<String> result = new ArrayList<>(noDuplicateResult);
        taskRepository.save(task);
        writeResult(result);
    }

    private static void writeResult(List<String> result) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("text.txt", true));
            int countLine = 0;
            bufferedWriter.write("Start work " + Thread.currentThread().getName() + "\n");
            for (String value : result) {
                countLine++;
                bufferedWriter.write(countLine + " " + value + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getResults() {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("text.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getCurrent() {
        List<String> result = new ArrayList<>();
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for ( Thread t : threadSet){
            result.add(t.getName());
        }

        return result;
    }


}
