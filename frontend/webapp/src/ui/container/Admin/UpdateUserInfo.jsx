import Modal from "../../components/common/Modal";

const UpdateUserInfo = (props) => {
  const { open, handleClose } = props;
  return (
    <Modal open={open} handleClose={handleClose}>
      <p>This is a Modal</p>
    </Modal>
  );
};

export default UpdateUserInfo;
