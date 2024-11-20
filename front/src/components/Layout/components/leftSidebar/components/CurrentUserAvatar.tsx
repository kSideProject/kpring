import Avatar from "boring-avatars";
import useModal from "../../../../common/modal/hooks/useModal";
import Modal from "../../../../common/modal/Modal";
import UserProfile from "../../../../user/components/UserProfile";

const CurrentUserAvatar = () => {
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <div>
      <Avatar name="Jihyun" variant="beam" size={44} onClick={openModal} />
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <UserProfile />
      </Modal>
    </div>
  );
};

export default CurrentUserAvatar;
