class User < ApplicationRecord
  # Include default devise modules. Others available are:
  # :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable,
         :confirmable
         
      has_many :posts, dependent: :destroy
      has_many :reactions, dependent: :destroy
      has_many :comments, dependent: :destroy
      has_many :votes, dependent: :destroy


      has_one_attached :avatar
      has_one_attached :cover_picture

      validates :username, presence: true, uniqueness: true, length: { in: 3..15 }
      validates :email, presence: true, uniqueness: true, format: Devise.email_regexp
      validates :bio, presence: true, length: { maximum: 200 }
      validates :password, presence: true, length: { in: 6..20 }
end
