class Comment < ApplicationRecord
  belongs_to :user
  belongs_to :post
  has_many :votes, dependent: :destroy

  def supports
    self.votes.filter{ |vote| vote.support }.length
  end

  def nonsupports
    self.votes.filter{ |vote| vote.nonsupport }.length
  end

  def supported(user)
    vote = votes.find_by(user_id: user.id)
    vote.nil? ? false : vote.support
  end

  def nonsupported(user)
    vote = votes.find_by(user_id: user.id)
    vote.nil? ? false : vote.nonsupport
  end
end
