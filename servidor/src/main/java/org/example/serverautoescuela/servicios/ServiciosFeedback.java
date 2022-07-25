package org.example.serverautoescuela.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.serverautoescuela.dao.DaoFeedback;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Feedback;
import org.example.serverautoescuela.domain.Info;

public class ServiciosFeedback {
    private final DaoFeedback dao;

    @Inject
    public ServiciosFeedback(DaoFeedback dao, ServiciosClase sc) {
        this.dao = dao;
    }

    //Get un feedback
    public Either<ApiError, Feedback> getFeedbackByClase(int idClase) {
        return dao.getOneFeedback(idClase);
    }

    //Add feedback
    public Either<ApiError, Feedback> saveFeedback(Feedback feedback) {
        return dao.postFeedback(feedback);
    }

    //delete feedback
    public Either<ApiError, Info> deleteFeedback(int idClase) {
        return dao.deleteFeedback(idClase);
    }

    //update feedback
    public Either<ApiError, Info> updateFeedback(Feedback feedback) {
        return dao.updateFeedback(feedback);
    }

    //Delete all feedback
    public Either<ApiError, Info> deleteAllFeedback(String dni) {
        return dao.deleteFeedbackMany(dni);
    }
}
