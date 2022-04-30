class VotesController < ApplicationController

      def support
        vote = Vote.find_by(user_id: current_user.id, comment_id: params[:comment_id])
    
        unless vote.nil?
          if vote.support
            vote.destroy
          else  
            update(vote, true)
          end
        else
          save(true)
        end
      end
    
      def nonsupport
        vote = Vote.find_by(user_id: current_user.id, comment_id: params[:comment_id])
    
        unless vote.nil?
          if vote.nonsupport
            vote.destroy
          else  
            update(vote, false)
          end
        else
          save(false)
        end
      end
    
      def save(support)
        comment = Comment.find(params[:comment_id])
        vote = comment.votes.build
        vote.user = current_user
        vote.support = support
        vote.nonsupport = !support
        vote.save
      end
    
      def update(vote, support)
        vote.support = support
        vote.nonsupport = !support
        vote.save
      end
    end
    