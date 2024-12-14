import { IoAddCircle } from "react-icons/io5";
import useModal from "../../../../common/modal/hooks/useModal";
import Modal from "../../../../common/modal/Modal";
import CreateServerForm from "../../../../Server/components/CreateServerForm";

const AddServer = () => {
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <div>
      <IoAddCircle fontSize="48" onClick={openModal} />
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <CreateServerForm />
      </Modal>
    </div>
  );
};

export default AddServer;
