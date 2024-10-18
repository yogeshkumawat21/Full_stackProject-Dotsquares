package com.App.Yogesh.Services;

import com.App.Yogesh.Models.Reels;
import com.App.Yogesh.Models.User;
import java.util.List;
public interface ReelsService {


    public Reels createReels(Reels reel , User user);
    public List<Reels> findAllReels();
    public List<Reels> findUsersReel(Integer userId) throws Exception;


}
